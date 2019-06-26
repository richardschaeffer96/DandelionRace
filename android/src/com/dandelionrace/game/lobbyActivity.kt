package com.dandelionrace.game

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.badlogic.gdx.Game
import com.dandelionrace.game.classes.DandelionGame
import com.dandelionrace.game.classes.GameAdapter
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot




class lobbyActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val gameList = database.getReference("games")
    var games: ArrayList<DandelionGame> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)


        val adapter = GameAdapter(this, games)
        val availableGames = findViewById<ListView>(R.id.availableGames)
        availableGames.setAdapter(adapter)






        //
        gameList.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                games.clear()

                for (child in dataSnapshot.children) {
                    val snap = child as DataSnapshot
                    val s = snap.children
                    val list: ArrayList<String> = ArrayList()
                    for (c in s) {
                        list.add(c.value.toString())
                    }
                    val o = DandelionGame(list[0],list[3].toInt(),list[2],list[1].toBoolean())
                    games.add(o)
                }

                adapter.notifyDataSetChanged()

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    fun createNewGame(view: View) {
        val n = findViewById<EditText>(R.id.editText).getText().toString()
        val path = "games/" + n
        val newGame = database.getReference(path)


        val g = DandelionGame(n,0,"MyName",true )

       // val old = txtView.getText().toString() +"\n" + msg.getText().toString()
        newGame.setValue(g)
    }

}
