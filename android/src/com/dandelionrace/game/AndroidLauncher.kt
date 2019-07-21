package com.dandelionrace.game

import android.app.ActionBar
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ActionMode
import android.view.View

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.dandelionrace.game.States.PlayState
import com.dandelionrace.game.sprites.Item
import com.dandelionrace.game.sprites.Tube

class AndroidLauncher() : AndroidApplication() {

    private var tubeString: String = ""
    private var itemString: String = ""

    //without multiplayer: following values are needed
    private val tubeCount: Int = 10
    private val tubeSpacing: Float = 125f
    private val width: Int = 800
    lateinit var tubeArrayList: ArrayList<Tube>
    lateinit var itemArrayList: ArrayList<Item>
    private val itemCount: Int = 8




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()




        mp = MediaPlayer.create(this,R.raw.rainbow_forest)
        pointer = MediaPlayer.create(this,R.raw.waterdrop)


        // get device dimensions
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var screenWidth = displayMetrics.widthPixels
        var screenHeight = displayMetrics.heightPixels
        
        isSingle = intent.getBooleanExtra("single", false);

        if(isSingle){
            tubeArrayList = arrayListOf<Tube>()
            itemArrayList = arrayListOf<Item>()
            for (i in 1..tubeCount){
                tubeArrayList.add(Tube(i*(tubeSpacing+width)))
            }
            for (i in 1..itemCount){
                itemArrayList.add(Item(i*(tubeSpacing+width), isSingle))
            }

            for (i in tubeArrayList){
                if(tubeArrayList.indexOf(i)==tubeArrayList.size-1){
                    tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y
                } else {
                    tubeString += "" + i.posTopTube.x + "$" + i.posTopTube.y + "$" + i.posBotTube.x + "$" + i.posBotTube.y + "%"
                }
            }

            for (i in itemArrayList){
                if(itemArrayList.indexOf(i)==itemArrayList.size-1){
                    itemString += "" + i.posItem.x + "$" + i.posItem.y + "$" + i.effect
                } else {
                    itemString += "" + + i.posItem.x + "$" + i.posItem.y + "$" + i.effect + "%"
                }
            }


            val str = getRandomString(20)
            initialize(dandelionrace(context, tubeString, itemString, "single" + str, "null", screenWidth, screenHeight), config)

        }


        if (!isSingle){
            tubeString = intent.getStringExtra("tubes")
            itemString = intent.getStringExtra("items")
            var gameName = intent.getStringExtra("game")
            var enemy = intent.getStringExtra("enemy")
            println("AL: "+ enemy)
            initialize(dandelionrace(context, tubeString, itemString, gameName, enemy, screenWidth, screenHeight), config)
        }
    }

    companion object {
        var isSingle: Boolean = false
        lateinit var mp:MediaPlayer
        lateinit var pointer:MediaPlayer
    }
    //When back key is pressed return to mainscreen
    override fun onBackPressed (){
        //super.onBackPressed()
        mp.stop()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        mp.stop()
    }

    fun getRandomString(length: Int) : String {
        val allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz"
        return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
    }


}
