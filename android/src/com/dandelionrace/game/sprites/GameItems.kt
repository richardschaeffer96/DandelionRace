package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.*

class GameItems(x: Float, y:Float) {


    var posItem: Vector2
    var itemPic: Texture

    var bounds: Rectangle

    var effect: String = "EFFEKT IST 0"

    var rand: Random

    init {
        posItem = Vector2(x, y)

        //TODO SET THE RIGHT ITEM PICTURES FOR THE DIFFERENT ITEMS

        rand = Random()
        var i: Int = rand.nextInt(ITEMCOUNT+1)
        if(i==1){
            effect = "EFFEKT IST 1"
            itemPic = Texture("itemtime.png")
        }else if (i==2){
            effect = "EFFEKT IST 2"
            itemPic = Texture("itemdiamond.png")
        }else{
            effect = "EFFEKT IST 3"
            itemPic = Texture("itemband.png")
        }

        bounds = Rectangle(posItem.x, posItem.y, itemPic.width.toFloat(), itemPic.height.toFloat())

        System.out.println(effect)

    }

    companion object {
        private val ITEMCOUNT = 3
    }

    fun collides(player: Rectangle):Boolean{
        return player.overlaps(bounds)
    }

}