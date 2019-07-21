package com.dandelionrace.game.lobby

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.dandelionrace.game.AndroidLauncher
import com.dandelionrace.game.R
import com.dandelionrace.game.classes.DandelionGame
import com.dandelionrace.game.classes.PlayerInGameAdapter
import com.dandelionrace.game.classes.PlayerOnServer
import com.dandelionrace.game.sprites.Item
import com.dandelionrace.game.sprites.Tube
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.snapshot.Index
import kotlinx.android.synthetic.main.activity_entry_hall.*
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class EntryHallActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var players: ArrayList<PlayerOnServer> = arrayListOf()
    private var mAuth = FirebaseAuth.getInstance()
    val mymail = mAuth?.currentUser?.email.toString()
    val myName = mAuth?.currentUser?.displayName.toString()
    val gamenameforlabel = ""
    //to change Button-Text when Player is ready
    var ready: Boolean = false
    lateinit var readyButton: ImageView
    var allPlayersReady: Boolean = false
    lateinit var thisgame: DandelionGame

    private val tubeCount: Int = 10
    private val tubeSpacing: Float = 125f
    private val width: Int = 800
    lateinit var tubeArrayList: ArrayList<Tube>
    lateinit var itemArrayList: ArrayList<Item>
    private val itemCount: Int = 8

    var tubeString: String = ""
    var itemString: String = ""

    var game: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisgame = DandelionGame("DUMMY", "0", "0", true, "o", true, "0")
        setContentView(R.layout.activity_entry_hall)
        game = intent.getStringExtra("game")
        val playerList = database.getReference("playersInGame/"+game)

        readyButton = ImageView(this)
        readyButton = this.findViewById(R.id.readybutton)

        //get the game i joint
        val gameDatabase = database.getReference("games/" + game)
        gameDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val snap = dataSnapshot.children
                val list: ArrayList<String> = ArrayList()
                for (s in snap) {
                    list.add(s.value.toString())
                }

                //initialize game one time at launch
                if (thisgame.dummy) {
                    thisgame = DandelionGame(list[2], list[6], list[5], list[4].toBoolean(), list[1], false, list[7])
                    thisgame.numberOfPlayers = list[3].toInt()
                    val gamenameforlabel = list[2]
                    findViewById<TextView>(R.id.gamename).setText("You are currently registered in the game " + gamenameforlabel + ".")

                    //If host == my own name, im host, so i send the seed
                    if (list[1] == myName && thisgame.seed == "0") {
                        //create TubeList
                        tubeArrayList = arrayListOf<Tube>()
                        itemArrayList = arrayListOf<Item>()
                        for (i in 1..tubeCount){
                            tubeArrayList.add(Tube(i*(tubeSpacing+width)))
                        }
                        for (i in 1..itemCount){
                            itemArrayList.add(Item(i*(tubeSpacing+width), false))
                        }

                        for (i in tubeArrayList){
                            if(tubeArrayList.indexOf(i)==tubeArrayList.size-1){
                                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y
                            } else {
                                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y + "%"
                            }
                        }

                        for (i in itemArrayList){
                            if(itemArrayList.indexOf(i)==itemArrayList.size-1){
                                itemString += "" + i.posItem.x + "$" + i.posItem.y + "$" + i.effect
                            } else {
                                itemString += "" + + i.posItem.x + "$" + i.posItem.y + "$" + i.effect + "%"
                            }
                        }

                        val setItems = database.getReference("games/" + game + "/xitems")
                        setItems.setValue(itemString)

                        val setSeed = database.getReference("games/" + game + "/seed")
                        setSeed.setValue(tubeString)

                    } else {
                        tubeString = list[6]
                    }
                } else {
                    thisgame.numberOfPlayers = list[3].toInt()
                    if (list[3].toInt() == 0) {
                        unsubscribeFromGame()
                        val intent = Intent(this@EntryHallActivity, lobbyActivity::class.java)
                        startActivity(intent)
                    }

                    if (list[4].toBoolean()==false && list[1]!=myName){
                        val intent = Intent(this@EntryHallActivity, AndroidLauncher::class.java)
                        intent.putExtra("tubes", tubeString)
                        intent.putExtra("game", game)
                        val playersString = database.getReference("games/"+game+"/player")
                        println(playersString)
                        var playerMails = ""
                        // fuegt den eigenen Namen der spielerliste hinzu
                        playersString.addListenerForSingleValueEvent(object: ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                println(dataSnapshot)
                                playerMails = dataSnapshot.getValue().toString()
                                intent.putExtra("enemy", playerMails)
                                intent.putExtra("items", thisgame.xItems)
                                startActivity(intent)
                            }

                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                        println("MAILS: "+playerMails)

                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })



        val adapter = PlayerInGameAdapter(this, players)


        //Laedt aktuelle Spieler des Spiels vom Server und legt Spieler-objekte an
        playerList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                players.clear()
                var ready = 0
                var newPlayers = arrayListOf<PlayerOnServer>()
                var incorrectPlayer: Boolean = false
                for (child in dataSnapshot.children) {
                    val snap = child as DataSnapshot
                    val s = snap.children
                    val list: ArrayList<String> = ArrayList()
                    for (c in s) {
                        list.add(c.value.toString())
                    }
                    //creates a new Playerobject and checks if player is already listed
                    //TODO: remove player when player leaves
                    val p: PlayerOnServer

                    try {
                        p = PlayerOnServer(list[2], list[1], list[0])
                        p.posx = list[3].toFloat()
                        p.posy = list[4].toFloat()
                        p.ready = list[5].toBoolean()
                        players.add(p)
                    } catch (e: IndexOutOfBoundsException) {
                        incorrectPlayer = true
                    }





                }
                adapter.notifyDataSetChanged()
                val playersInGame = findViewById<ListView>(R.id.playersInGame)
                playersInGame.setAdapter(adapter)

                //check if every player is ready
                var r = true
                for (pl in players) {
                    if (!pl.ready) {
                        r = false
                    }
                }
                if (r) {
                    allPlayersReady = true
                } else {
                    allPlayersReady = false
                }

                if (incorrectPlayer) {
                    allPlayersReady = false
                }
                //check if client is host of session and can start game
                //println("Host: " + thisgame?.host)
                //println("NAME: " + myName)
                if (thisgame?.host == myName && allPlayersReady) {
                    findViewById<ImageView>(R.id.startGame).setVisibility(View.VISIBLE)
                } else {
                    findViewById<ImageView>(R.id.startGame).setVisibility(View.INVISIBLE)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    //Player klicks ready, sets Value in Database and changes Text of Button
    fun setReady(view: View) {
        val game = intent.getStringExtra("game")
        val nameForPlayerDatabase = intent.getStringExtra("nameForPlayerDatabase")
        val path = "playersInGame/" + game + "/" + nameForPlayerDatabase + "/ready"
        val newGame = database.getReference(path)
        if (ready) {
            newGame.setValue("false")
            readyButton.setImageDrawable(resources.getDrawable(R.drawable.ready))
            ready = false
        } else {
            newGame.setValue("true")
            ready = true
            readyButton.setImageDrawable(resources.getDrawable(R.drawable.leave))
        }

    }

    //When back key is pressed return to lobby and unregister from game
    override fun onBackPressed (){
        super.onBackPressed()
        if (myName == thisgame.host) {
            //TODO: Player is host, game needs to be ended and everyone should be thrown out of entry hall

            val game = intent.getStringExtra("game")
            //get the game i joint
            val gameDatabase = database.getReference("games/" + game + "/numberOfPlayers")
            gameDatabase.setValue(0)


        } else {
            unsubscribeFromGame()
        }
    }

    fun unsubscribeFromGame() {
        val path = "games/" + thisgame.name + "/numberOfPlayers"
        var newNumberOfPlayers = 0
        if (thisgame.numberOfPlayers > 0) {
            newNumberOfPlayers = thisgame.numberOfPlayers -1
        }

        val reducePlayer = database.getReference(path)
        reducePlayer.setValue(newNumberOfPlayers)
        val nameToDelete = createPlayerNameForDatabase(mymail)
        val pathdelete = "playersInGame/" + thisgame.name + "/" +nameToDelete
        val deletePlayer = database.getReference(pathdelete)
        deletePlayer.removeValue()
    }

    private fun createPlayerNameForDatabase(mymail: String) : String {
        //Use the mail for playeridentification, eliminate . for database reasons
        val nameForPlayerDatabaseArr = mymail.split(".");
        var nameForPlayerDatabase = ""
        for (na in nameForPlayerDatabaseArr) {
            nameForPlayerDatabase = nameForPlayerDatabase + na
        }
        return nameForPlayerDatabase
    }

    fun startGame(v: View){
        val intent = Intent(this, AndroidLauncher::class.java)
        intent.putExtra("tubes", tubeString)
        intent.putExtra("items", itemString)
        intent.putExtra("game", game)
        val playersString = database.getReference("games/"+game+"/player")
        println(playersString)
        var playerMails = ""
        // fuegt den eigenen Namen der spielerliste hinzu
        playersString.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println(dataSnapshot)
                playerMails = dataSnapshot.getValue().toString()
                intent.putExtra("enemy", playerMails)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
        println("MAILS: "+playerMails)

        val newGame = database.getReference("games/" + game + "/open")
        newGame.setValue(false)
    }
}
