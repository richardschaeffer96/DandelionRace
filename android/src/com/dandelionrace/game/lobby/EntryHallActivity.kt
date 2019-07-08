package com.dandelionrace.game.lobby

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.dandelionrace.game.AndroidLauncher
import com.dandelionrace.game.R
import com.dandelionrace.game.classes.DandelionGame
import com.dandelionrace.game.classes.PlayerInGameAdapter
import com.dandelionrace.game.classes.PlayerOnServer
import com.dandelionrace.game.sprites.Tube
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_entry_hall.*
import java.lang.Exception

class EntryHallActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var players: ArrayList<PlayerOnServer> = arrayListOf()
    private var mAuth = FirebaseAuth.getInstance()
    val mymail = mAuth?.currentUser?.email.toString()
    val myName = mAuth?.currentUser?.displayName.toString()
    val gamenameforlabel = ""
    //to change Button-Text when Player is ready
    var ready: Boolean = false
    var allPlayersReady: Boolean = false
    lateinit var thisgame: DandelionGame

    private val tubeCount: Int = 5
    private val tubeSpacing: Float = 125f
    private val width: Int = 300
    lateinit var tubeArrayList: ArrayList<Tube>

    var tubeString: String = ""
    var game: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisgame = DandelionGame("DUMMY", "0", "0", true, "o", true)
        setContentView(R.layout.activity_entry_hall)
        game = intent.getStringExtra("game")
        val readybutton = findViewById<Button>(R.id.readybutton)
        val playerList = database.getReference("playersInGame/"+game)

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
                    thisgame = DandelionGame(list[2], list[6], list[5], list[4].toBoolean(), list[1], false)
                    thisgame.numberOfPlayers = list[3].toInt()
                    val gamenameforlabel = list[2]
                    findViewById<TextView>(R.id.gamename).setText("Du befindest dich im Spiel " + gamenameforlabel)

                    //If host == my own name, im host, so i send the seed
                    //TODO: parse seed to string
                    if (list[1] == myName && thisgame.seed == "0") {
                        //create TubeList
                        tubeArrayList = arrayListOf<Tube>()
                        for (i in 1..tubeCount){
                            tubeArrayList.add(Tube(i*(tubeSpacing+width)))
                        }

                        //TODO: change val tubeArrayList into the String variant

                        for (i in tubeArrayList){
                            if(tubeArrayList.indexOf(i)==tubeArrayList.size-1){
                                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y
                            } else {
                                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y + "%"
                            }
                        }

                        val setSeed = database.getReference("games/" + game + "/seed")
                        setSeed.setValue(tubeString)

                    } else {
                        //TODO: Im not host, so i have to download and parse the seed (stored in list[6]
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
        val playersInGame = findViewById<ListView>(R.id.playersInGame)
        playersInGame.setAdapter(adapter)

        //Laedt aktuelle Spieler des Spiels vom Server und legt Spieler-objekte an
        playerList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var ready = 0
                for (child in dataSnapshot.children) {
                    val snap = child as DataSnapshot
                    val s = snap.children
                    val list: ArrayList<String> = ArrayList()
                    for (c in s) {
                        list.add(c.value.toString())
                    }
                    //creates a new Playerobject and checks if player is already listed
                    //TODO: remove player when player leaves
                    var newPlayers = arrayListOf<PlayerOnServer>()
                    val p = PlayerOnServer(list[2], list[1], list[0])
                        p.posx = list[3].toFloat()
                        p.posy = list[4].toFloat()
                        p.ready = list[5].toBoolean()
                        newPlayers.add(p)


                    players = newPlayers;
                }

                adapter.notifyDataSetChanged()

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
                //check if client is host of session and can start game
                //println("Host: " + thisgame?.host)
                //println("NAME: " + myName)
                if (thisgame?.host == myName && allPlayersReady) {
                    findViewById<Button>(R.id.startGame).setVisibility(View.VISIBLE)
                } else {
                    findViewById<Button>(R.id.startGame).setVisibility(View.INVISIBLE)
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
            ready = false
            readybutton.text = "Bereit"
        } else {
            newGame.setValue("true")
            ready = true
            readybutton.text = "doch nicht bereit"
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
