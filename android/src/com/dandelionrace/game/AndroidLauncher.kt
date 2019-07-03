package com.dandelionrace.game

import android.content.Context
import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.dandelionrace.game.sprites.GameTubes
import com.dandelionrace.game.sprites.Tube

class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()

        var tubeString: String = intent.getStringExtra("tubes")

        var finalTubes: ArrayList<GameTubes>
        finalTubes = ArrayList<GameTubes>()

        var tubes = tubeString.split("%")

        for(t in tubes){
            var tubes2: List<String> = t.split("$")
            finalTubes.add(GameTubes(tubes2[0].toFloat(), tubes2[1].toFloat(), tubes2[2].toFloat(), tubes2[3].toFloat()))
        }

        initialize(dandelionrace(context, finalTubes), config)
    }
}
