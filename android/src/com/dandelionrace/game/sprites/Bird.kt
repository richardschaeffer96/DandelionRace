package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3

class Bird(x: Int, y: Int) {
    var position: Vector3
    private val velocity: Vector3
    private val bound: Rectangle
    var status: String = "free"
    var trappedTube: String = ""
    var birdAnimation: Animations
    var texture: Texture

    init {
        status = "free"
        trappedTube = ""
        position = Vector3(x.toFloat(), y.toFloat(), 0f)
        velocity = Vector3(0f, 0f, 0f)
        texture = Texture("bugredanimation.png")
        bound = Rectangle(x.toFloat(), y.toFloat(), texture.width.toFloat() / 2, texture.height.toFloat() / 2)
        birdAnimation = Animations(TextureRegion(texture), 2, 0.5f)
    }

    fun update(dt: Float) {
        birdAnimation.update(dt)
        if(status=="free"){
            if (position.y > 0)
                velocity.add(0f, GRAVITY.toFloat(), 0f)
            velocity.scl(dt)
            position.add(MOVEMENT *dt, velocity.y, 0f)
            if(position.y < 0)
                position.y = 0f

            velocity.scl(1 / dt)
            bound.setPosition(position.x,position.y)
        } else if(status=="trapped"){

        }

    }

    companion object {

        private val MOVEMENT = 100
        private val GRAVITY = -15
    }

    fun jump(){
        if (status=="free")
            velocity.y = 800f;
    }
    fun getBound():Rectangle{
        return bound
    }
}
