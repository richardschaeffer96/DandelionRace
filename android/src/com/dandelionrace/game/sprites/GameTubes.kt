package com.dandelionrace.game.sprites


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dandelionrace.game.States.WinGame
import org.w3c.dom.Text

import java.util.Random

class GameTubes(posTopTubeX: Float, posTopTubeY: Float,posBotTubeX: Float, posBotTubeY: Float) {



    val posTopTube: Vector2
    val posBotTube: Vector2
    val boundsTop: Rectangle
    val boundsBot: Rectangle
    var topTube: Texture
    var bottomTube: Texture

    private val rand: Random


    init {
        topTube = Texture("weblong.png")
        bottomTube = Texture("busch.png")


        rand = Random()

        posBotTube = Vector2(posBotTubeX, posBotTubeY)
        posTopTube = Vector2(posTopTubeX, posTopTubeY)

        boundsTop = Rectangle(posTopTube.x, posTopTube.y, topTube.width.toFloat(), topTube.height.toFloat())
        boundsBot = Rectangle(posBotTube.x,posBotTube.y, bottomTube.width.toFloat(), bottomTube.height.toFloat()-100f)
    }

    companion object {
    }


    fun collides(player: Rectangle):Boolean{
        return player.overlaps(boundsTop) || player.overlaps(boundsBot)
    }
}
