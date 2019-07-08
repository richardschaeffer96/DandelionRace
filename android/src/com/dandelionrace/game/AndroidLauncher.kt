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
    private val tubeCount: Int = 4
    private val tubeSpacing: Float = 300f
    private val width: Int = 300
    lateinit var tubeArrayList: ArrayList<Tube>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()


        tubeArrayList = arrayListOf<Tube>()
        for (i in 1..tubeCount){
            tubeArrayList.add(Tube(i*(tubeSpacing+width)))
        }


        for (i in tubeArrayList){
            if(tubeArrayList.indexOf(i)==tubeArrayList.size-1){
                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y
            } else {
                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y + "%"
            }
        }


        initialize(dandelionrace(context, tubeString), config)
    }
}
