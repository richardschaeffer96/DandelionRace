package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dandelionrace.game.sprites.Tube.Companion.LOWEST_OPENING
import com.dandelionrace.game.sprites.Tube.Companion.TUBE_GAP
import java.util.*

class Item(x: Float, isSingle: Boolean) {


    val posItem: Vector2

    private var rand: Random

    var singlePlayer: Boolean = isSingle

    var effect: String = "EFFEKT IST 0"

    init {

        if (singlePlayer==true){
            ITEMCOUNT=3
        }

        rand = Random()

        posItem = Vector2(x + 200, (rand.nextInt(FLUCTUATION) + 200).toFloat())

        System.out.println("ITEMCOUNT IST: " + ITEMCOUNT)

        rand = Random()
        var i: Int = rand.nextInt(ITEMCOUNT+1)

        System.out.println("ITEMCOUNT IST: " + ITEMCOUNT)
        System.out.println("ITEM RANDOM IST: " + i)

        if(i==1) {
            effect = "slow"
            //itemPic = Texture("bluemushroom.png")
        }else if(i==4){
            effect = "switch"
            //itemPic = Texture("switch.png")
        }else if (i==5){
            effect = "leaves"
            //itemPic = Texture("leaves.png")
        }else if (i==2){
            effect = "ghost"
            //itemPic = Texture("ghost.png")
        }else{
            effect = "speed"
            //itemPic = Texture("greenmushroom.png")
        }

        //posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - 10f)


    }

    companion object {
        private val FLUCTUATION = 1000
        private var ITEMCOUNT = 5

    }


}