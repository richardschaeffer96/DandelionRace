package com.dandelionrace.game

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.*


class lobbyActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("message")
    lateinit var txtView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lobby)

            txtView = findViewById<TextView>(R.id.chatView)

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                txtView.text = dataSnapshot.getValue(String::class.java)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })


    }

    fun writeData(view: View) {
        val msg = findViewById<EditText>(R.id.editText)
        myRef.setValue(msg.getText().toString())
    }

}
