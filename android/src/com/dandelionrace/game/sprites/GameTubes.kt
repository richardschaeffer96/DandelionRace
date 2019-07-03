package com.dandelionrace.game.sprites


import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dandelionrace.game.States.WinGame

import java.util.Random

class GameTubes(posTopTubeX: Float, posTopTubeY: Float,posBotTubeX: Float, posBotTubeY: Float) {


    val topTube: Texture
    val bottomTube: Texture
    val posTopTube: Vector2
    val posBotTube: Vector2
    private val boundsTop: Rectangle
    private val boundsBot: Rectangle


    init {
        topTube = Texture("spidertop.png")
        bottomTube = Texture("spiderbottom.png")

        posBotTube = Vector2(posBotTubeX, posBotTubeY)
        posTopTube = Vector2(posTopTubeX, posTopTubeY)

        boundsTop = Rectangle(posTopTube.x, posTopTube.y, topTube.width.toFloat(), topTube.height.toFloat())
        boundsBot = Rectangle(posBotTube.x,posBotTube.y, bottomTube.width.toFloat(), bottomTube.height.toFloat())
    }

    companion object {
        val TUBE_WIDTH : Int = 300
        private val FLUCTUATION = 130
        val TUBE_GAP = 700
        val LOWEST_OPENING = -400
    }


    fun collides(player: Rectangle):Boolean{
        return player.overlaps(boundsTop) || player.overlaps(boundsBot)
    }
}
