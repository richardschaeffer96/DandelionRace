package com.dandelionrace.game

import android.content.Context
import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.graphics.Texture
import com.dandelionrace.game.sprites.GameTubes
import com.dandelionrace.game.sprites.Item
import com.dandelionrace.game.sprites.Tube

class AndroidLauncher : AndroidApplication() {

    private var tubeString: String = ""
    private var itemString: String = ""

    //without multiplayer: following values are needed
    private val tubeCount: Int = 5
    private val tubeSpacing: Float = 125f
    private val width: Int = 300
    lateinit var tubeArrayList: ArrayList<Tube>
    lateinit var itemArrayList: ArrayList<Item>
    private val itemCount: Int = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()

        tubeArrayList = arrayListOf<Tube>()
        itemArrayList = arrayListOf<Item>()
        for (i in 1..tubeCount){
            tubeArrayList.add(Tube(i*(tubeSpacing+width)))
        }
        for (i in 1..itemCount){
            itemArrayList.add(Item(i*(tubeSpacing+width)))
        }

        //TODO: change val tubeArrayList into the String variant

        for (i in tubeArrayList){
            if(tubeArrayList.indexOf(i)==tubeArrayList.size-1){
                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y
            } else {
                tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y + "%"
            }
        }

        for (i in itemArrayList){
            if(itemArrayList.indexOf(i)==itemArrayList.size-1){
                itemString += "" + i.posItem.x + "$" + i.posItem.y
            } else {
                itemString += "" + + i.posItem.x + "$" + i.posItem.y + "%"
            }
        }

        //for Multiplayer only this code
        //tubeString = intent.getStringExtra("tubes")
        //itemString = intent.getStringExtra("items")

        initialize(dandelionrace(context, tubeString, itemString), config)
    }
}
