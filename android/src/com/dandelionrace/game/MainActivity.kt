package com.dandelionrace.game

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* println("Start"); */
        setContentView(R.layout.activity_main)
    }

    fun switchToGame(view: View) {
        val intent = Intent(this@MainActivity, AndroidLauncher::class.java)
        startActivity(intent)
    }

    fun switchToLobby(view: View) {
        val intent = Intent(this@MainActivity, lobbyActivity::class.java)
        startActivity(intent)
    }
}
