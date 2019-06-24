package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2

import java.util.Random

class Tube(x: Float) {


    val topTube: Texture
    val bottomTube: Texture
    val posTopTube: Vector2
    val posBotTube: Vector2
    private val rand: Random

    init {
        topTube = Texture("toptube.png")
        bottomTube = Texture("bottomtube.png")
        rand = Random()

        posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - bottomTube.height.toFloat())
    }

    companion object {
        val TUBE_WIDTH : Int = 52
        private val FLUCTUATION = 130
        val TUBE_GAP = 1000
        val LOWEST_OPENING = -400
    }


    fun reposition(x: Float){
        posTopTube.set(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        posBotTube.set(x + 400, posTopTube.y - TUBE_GAP.toFloat() - bottomTube.height.toFloat())
    }
}
