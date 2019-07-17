package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.*

class GameItems(x: Float, y:Float, item:String) {


    var posItem: Vector2
    var itemPic: Texture

    var bounds: Rectangle

    var effect: String = item


    init {
        posItem = Vector2(x, y)

        if(effect == "slow") {
            itemPic = Texture("bluemushroom.png")
        }else if(effect == "switch"){
            itemPic = Texture("switchpic.png")
        }else if (effect == "leaves"){
            itemPic = Texture("leaves.png")
        }else if (effect == "ghost"){
            itemPic = Texture("ghost.png")
        }else{
            itemPic = Texture("greenmushroom.png")
        }

        bounds = Rectangle(posItem.x, posItem.y, itemPic.width.toFloat(), itemPic.height.toFloat())

        System.out.println(effect)

    }

    companion object {

    }

    fun collides(player: Rectangle):Boolean{
        return player.overlaps(bounds)
    }

}