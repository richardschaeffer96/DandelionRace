package com.dandelionrace.game

import android.content.Context
import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.dandelionrace.game.sprites.Tube

class AndroidLauncher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()

        val tubes_String = intent.getStringExtra("array")
        val tubes: ArrayList<Tube> = ArrayList<Tube>()

        initialize(dandelionrace(context, tubes), config)
    }
}
