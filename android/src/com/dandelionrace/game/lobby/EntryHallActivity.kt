package com.dandelionrace.game.lobby

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

class EntryHallActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var players: ArrayList<PlayerOnServer> = arrayListOf()
    private var mAuth = FirebaseAuth.getInstance()
    val mymail = mAuth?.currentUser?.email.toString()
    val gamenameforlabel = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_hall)
        val game = intent.getStringExtra("game")
        val playerList = database.getReference("playersInGame/"+game)
        var thisgame: DandelionGame? = null

        //get the game i joint
        val gameDatabase = database.getReference("games/" + game)
        gameDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val snap = dataSnapshot.children
                val list: ArrayList<String> = ArrayList()
                for (s in snap) {
                    list.add(s.value.toString())
                }

                if (thisgame == null) {
                    thisgame = DandelionGame(list[1], list[5].toInt(), list[5], list[4].toBoolean(), list[0])
                    val gamenameforlabel = list[1]
                    findViewById<TextView>(R.id.gamename).setText("Du befindest dich im Spiel " + gamenameforlabel)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        //val currentGame = DandelionGame()


        val adapter = PlayerInGameAdapter(this, players)
        val playersInGame = findViewById<ListView>(R.id.playersInGame)
        playersInGame.setAdapter(adapter)

        //Laedt aktuelle Spieler des Spiels vom Server und legt Spieler-objekte an
        playerList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    val snap = child as DataSnapshot
                    val s = snap.children
                    val list: ArrayList<String> = ArrayList()
                    for (c in s) {
                        list.add(c.value.toString())
                    }
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
        println(path)
        newGame.setValue("true")
    }
}
