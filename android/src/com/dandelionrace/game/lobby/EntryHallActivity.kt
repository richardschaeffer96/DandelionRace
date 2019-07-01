package com.dandelionrace.game.lobby

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.dandelionrace.game.R
import com.dandelionrace.game.classes.DandelionGame
import com.dandelionrace.game.classes.PlayerInGameAdapter
import com.dandelionrace.game.classes.PlayerOnServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_entry_hall.*

class EntryHallActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var players: ArrayList<PlayerOnServer> = arrayListOf()
    private var mAuth = FirebaseAuth.getInstance()
    val mymail = mAuth?.currentUser?.email.toString()
    val myName = mAuth?.currentUser?.displayName.toString()
    val gamenameforlabel = ""
    //If this Player is ready, changes the buttontext of ready
    var ready: Boolean = false
    var allPlayersReady: Boolean = false
    lateinit var thisgame: DandelionGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        thisgame = DandelionGame("DUMMY", 0, "0", true, "o", true)
        setContentView(R.layout.activity_entry_hall)
        val game = intent.getStringExtra("game")
        val playerList = database.getReference("playersInGame/"+game)
        val readybutton = findViewById<Button>(R.id.readybutton)

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
                    thisgame = DandelionGame(list[1], list[5].toInt(), list[5], list[4].toBoolean(), list[0], false)
                    thisgame.numberOfPlayers = list[2].toInt()
                    val gamenameforlabel = list[1]
                    findViewById<TextView>(R.id.gamename).setText("Du befindest dich im Spiel " + gamenameforlabel)


                } else {
                    println("------------" + list[5] )
                    thisgame.numberOfPlayers = list[2].toInt()
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
                    val p = PlayerOnServer(list[2], list[1], list[0])
                    var exists = false
                    for (pl in players) {
                        if (pl.mail == p.mail) {
                            pl.posx = list[3].toInt()
                            pl.posy = list[4].toInt()
                            pl.ready = list[5].toBoolean()


                            exists = true
                            break
                        }
                    }
                    if (!exists) {
                        p.posx = list[3].toInt()
                        p.posy = list[4].toInt()
                        p.ready = list[5].toBoolean()
                        players.add(p)
                    }

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
                println("Host: " + thisgame?.host)
                println("NAME: " + myName)
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
        if (myName == thisgame.host + "###") {
            //Player is host, game needs to be ended and everyone should be thrown out of entry hall
        } else {

            val path = "games/" + thisgame.name + "/numberOfPlayers"
            val newNumberOfPlayers = thisgame.numberOfPlayers -1
            val reducePlayer = database.getReference(path)
            reducePlayer.setValue(newNumberOfPlayers)
            val nameToDelete = createPlayerNameForDatabase(mymail)
            val pathdelete = "playersInGame/" + thisgame.name + "/" +nameToDelete
            val deletePlayer = database.getReference(pathdelete)
            deletePlayer.removeValue()
        }


    }

    fun createPlayerNameForDatabase(mymail: String) : String {
        //Use the mail for playeridentification, eliminate . for database reasons
        val nameForPlayerDatabaseArr = mymail.split(".");
        var nameForPlayerDatabase = ""
        for (na in nameForPlayerDatabaseArr) {
            nameForPlayerDatabase = nameForPlayerDatabase + na
        }
        return nameForPlayerDatabase
    }
}
