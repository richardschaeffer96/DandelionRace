package com.dandelionrace.game.sprites

import com.badlogic.gdx.math.Vector2
import com.dandelionrace.game.sprites.Tube.Companion.LOWEST_OPENING
import com.dandelionrace.game.sprites.Tube.Companion.TUBE_GAP
import java.util.*

class Item(x: Float) {


    val posItem: Vector2

    private val rand: Random


    init {

        rand = Random()

        posItem = Vector2(x + 200, (rand.nextInt(FLUCTUATION) + 200).toFloat())

        //posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - 10f)


    }

    companion object {
        private val FLUCTUATION = 1000
    }


}