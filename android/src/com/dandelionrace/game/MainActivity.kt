package com.dandelionrace.game

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.dandelionrace.game.lobby.lobbyActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private var mAuth = FirebaseAuth.getInstance()
    lateinit var tv_main_email: TextView
    lateinit var tv_main_name: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* println("Start"); */
        setContentView(R.layout.activity_main)
        tv_main_email = findViewById<TextView>(R.id.tv_main_email)
        tv_main_email.setText(mAuth?.currentUser?.email)
        tv_main_name = findViewById<TextView>(R.id.tv_main_name)
        tv_main_name.setText(mAuth?.currentUser?.displayName)
    }

    fun switchToSingle(view: View) {
        val intent = Intent(this@MainActivity, AndroidLauncher::class.java)
        intent.putExtra("single", true);
        startActivity(intent)
    }

    fun switchToLobby(view: View) {
        val intent = Intent(this@MainActivity, lobbyActivity::class.java)
        startActivity(intent)
    }

    fun logout(view : View){
        mAuth?.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun howto(view: View){
        val intent = Intent(this@MainActivity, HowTo::class.java)
        startActivity(intent)
    }

}
