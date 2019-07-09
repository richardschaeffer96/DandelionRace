package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.*

class GameItems(x: Float, y:Float) {


    var posItem: Vector2
    var itemPic: Texture

    var bounds: Rectangle

    init {
        posItem = Vector2(x, y)
        itemPic = Texture("itemtime.png")

        bounds = Rectangle(posItem.x, posItem.y, itemPic.width.toFloat(), itemPic.height.toFloat())

        //posTopTube = Vector2(x + 400, (rand.nextInt(FLUCTUATION) + TUBE_GAP + LOWEST_OPENING + 900).toFloat())
        //posBotTube = Vector2(x + 400, posTopTube.y - TUBE_GAP.toFloat() - 10f)


    }

    companion object {
        private val FLUCTUATION = 3000
    }

    fun collides(player: Rectangle):Boolean{
        return player.overlaps(bounds)
    }

}