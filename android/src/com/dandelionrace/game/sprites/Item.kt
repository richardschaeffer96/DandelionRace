package com.dandelionrace.game.sprites

import com.badlogic.gdx.math.Vector2
import java.util.*

class Item(posTopTube: Vector2, posBotTube: Vector2) {


    val posItem: Vector2

    private val rand: Random


    init {

        rand = Random()

        posItem = Vector2()

        //posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - 10f)


    }

    companion object {

    }


}