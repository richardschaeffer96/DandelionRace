package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dandelionrace.game.States.WinGame
import com.dandelionrace.game.dandelionrace

import java.util.Random

class Tube(x: Float) {


    //val topTube: Texture
    //val bottomTube: Texture
    val posTopTube: Vector2
    val posBotTube: Vector2
    //private val boundsTop: Rectangle
    //private val boundsBot: Rectangle
    private val rand: Random

    init {
       //topTube = Texture("spidertop.png")
       //bottomTube = Texture("spiderbottom.png")
        rand = Random()



        //posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + 1000 + LOWEST_OPENING + 900).toFloat())
        //TODO: CHECK WHY +400
        posTopTube = Vector2(x, dandelionrace.HEIGHT.toFloat() - TOP_POS - rand.nextInt(VARIATION_TOP_Y))
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - 10f)
        posBotTube = Vector2(x+ (rand.nextInt(VARIATION_BOT_X)).toFloat(), (1 - rand.nextInt(BOT_POS)).toFloat())
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - bottomTube.height.toFloat())

        //boundsTop = Rectangle(posTopTube.x, posTopTube.y, topTube.width.toFloat(), topTube.height.toFloat())
        //boundsBot = Rectangle(posBotTube.x,posBotTube.y, bottomTube.width.toFloat(), bottomTube.height.toFloat())

    }

    companion object {
        private val FLUCTUATION = 700
        val TUBE_GAP = 1500
        val LOWEST_OPENING = -700
        val VARIATION_TOP_Y = 500
        val VARIATION_BOT_X = 200
        val TOP_POS = 600
        val  BOT_POS = 300
    }

    /*
    fun reposition(x: Float){
        posTopTube.set(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        posBotTube.set(x + 400, posTopTube.y - TUBE_GAP.toFloat() - bottomTube.height.toFloat())
        boundsTop.setPosition(posTopTube.x,posBotTube.y)
        boundsBot.setPosition(posBotTube.x,posBotTube.y)

        rout_length = rout_length+1
        println("Anzahl:"+rout_length)

    }


    fun collides(player: Rectangle):Boolean{
        return player.overlaps(boundsTop) || player.overlaps(boundsBot)
    }
    */
}
