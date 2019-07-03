package com.dandelionrace.game.lobby

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.ListView
import com.dandelionrace.game.R
import com.dandelionrace.game.classes.DandelionGame
import com.dandelionrace.game.classes.GameAdapter
import com.dandelionrace.game.classes.PlayerOnServer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot



class lobbyActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val gameList = database.getReference("games")
    var games: ArrayList<DandelionGame> = arrayListOf()
    private var mAuth = FirebaseAuth.getInstance()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

        var playersInGame=""

        val adapter = GameAdapter(this, games)
        val availableGames = findViewById<ListView>(R.id.availableGames)
        availableGames.setAdapter(adapter)


        //
        gameList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                games.clear()
                //List games for join list
                for (child in dataSnapshot.children) {
                    val snap = child as DataSnapshot
                    val s = snap.children
                    val list: ArrayList<String> = ArrayList()
                    for (c in s) {
                        list.add(c.value.toString())
                    }
                    val o = DandelionGame(list[2],list[6],list[5],list[4].toBoolean(), list[1], false)
                    o.numberOfPlayers = list[3].toInt()
                    games.add(o)
                }

                adapter.notifyDataSetChanged()

                //Eventlistener fuer klicken auf spiel in liste
                availableGames.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                    // Get the selected item text from ListView (DandelionGame)
                    val selectedItem = parent.getItemAtPosition(position) as DandelionGame
                    val myname = mAuth?.currentUser?.displayName.toString()
                    val mymail = mAuth?.currentUser?.email.toString()
                    val gamename = selectedItem.name

                    val nameForPlayerDatabase = createPlayerNameForDatabase(mymail!!)

                    // Join game
                    val path = "games/" + gamename + "/player"
                    val pathforCount = "games/" + gamename + "/numberOfPlayers"
                    val numberOfPlayer = selectedItem.numberOfPlayers + 1
                    val newGame = database.getReference(path)
                    val newGameIncrementPlayer = database.getReference(pathforCount)

                    // fuegt den eigenen Namen der spielerliste hinzu
                    newGame.setValue(playersInGame + "," + myname)
                    //increments playernumber
                    newGameIncrementPlayer.setValue(numberOfPlayer)

                    registratePlayerInGame(gamename, nameForPlayerDatabase, myname!!, mymail!!)
                }

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun registratePlayerInGame(gamename: String, nameForPlayerDatabase: String, myname: String, mymail: String) {
        val newplayerpath = "playersInGame/"+ gamename+"/" + nameForPlayerDatabase
        val newplayerRef = database.getReference(newplayerpath)

        //erstellt fuer den eigenen Spieler einen Player auf dem Server
        var player = PlayerOnServer(myname, mymail, gamename)
        newplayerRef.setValue(player)

        //switch to Entry hall
        val intent = Intent(this@lobbyActivity, EntryHallActivity::class.java)
        intent.putExtra("game", gamename);
        intent.putExtra("nameForPlayerDatabase", nameForPlayerDatabase)
        startActivity(intent)
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

    fun createNewGame(view: View) {
        val name = mAuth?.currentUser?.displayName
        val mail = mAuth?.currentUser?.email
        val n = findViewById<EditText>(R.id.editText).getText().toString()
        val path = "games/" + n
        val newGame = database.getReference(path)
        val g = DandelionGame(n,"0",""+mail,true, ""+name, false)
        val nameForPlayerDatabase = createPlayerNameForDatabase(mail!!)
        newGame.setValue(g)
        findViewById<EditText>(R.id.editText).setText("")

        registratePlayerInGame(n, nameForPlayerDatabase!!, name!!, mail!!)
    }

}
