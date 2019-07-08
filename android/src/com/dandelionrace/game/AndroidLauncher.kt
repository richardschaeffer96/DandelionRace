package com.dandelionrace.game

import android.content.Context
import android.os.Bundle
import android.widget.Button

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.graphics.Texture
import com.dandelionrace.game.sprites.GameTubes
import com.dandelionrace.game.sprites.Tube

class AndroidLauncher : AndroidApplication() {

    var tubeString: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()


        tubeString = "825.0$1277.0$825.0$267.0%1250.0$1273.0$1250.0$263.0%1675.0$1320.0$1675.0$310.0%2100.0$1223.0$2100.0$213.0%2525.0$1294.0$2525.0$284.0"//intent.getStringExtra("tubes")
        var gameName = "tetstes" //intent.getStringExtra("game")

        initialize(dandelionrace(context, tubeString, gameName), config)
    }
}
