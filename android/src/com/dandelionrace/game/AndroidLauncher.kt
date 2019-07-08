package com.dandelionrace.game

import android.content.Context
import android.os.Bundle

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


        tubeString = intent.getStringExtra("tubes")


        initialize(dandelionrace(context, tubeString), config)
    }
}
