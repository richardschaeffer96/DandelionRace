package com.dandelionrace.game

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration

class AndroidLauncher : AndroidApplication() {

    var tubeString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()


        tubeString = intent.getStringExtra("tubes")
        var gameName = intent.getStringExtra("game")
        var enemy = intent.getStringExtra("enemy")
        println("AL: "+ enemy)

        initialize(dandelionrace(context, tubeString, gameName, enemy), config)
    }
}
