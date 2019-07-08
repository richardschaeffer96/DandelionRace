package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dandelionrace.game.States.WinGame

import java.util.Random

class Tube(x: Float) {


    //val topTube: Texture
    //val bottomTube: Texture
    val posTopTube: Vector2
    val posBotTube: Vector2
    //private val boundsTop: Rectangle
    //private val boundsBot: Rectangle
    private val rand: Random
    var rout_length: Int =0

    init {
       //topTube = Texture("spidertop.png")
       //bottomTube = Texture("spiderbottom.png")
        rand = Random()

        posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - 10f)
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - bottomTube.height.toFloat())

        //boundsTop = Rectangle(posTopTube.x, posTopTube.y, topTube.width.toFloat(), topTube.height.toFloat())
        //boundsBot = Rectangle(posBotTube.x,posBotTube.y, bottomTube.width.toFloat(), bottomTube.height.toFloat())

    }

    companion object {
        val TUBE_WIDTH : Int = 300
        private val FLUCTUATION = 300
        val TUBE_GAP = 1200
        val LOWEST_OPENING = -700
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
