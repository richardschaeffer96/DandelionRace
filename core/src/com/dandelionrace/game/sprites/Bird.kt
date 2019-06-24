package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector3

class Bird(x: Int, y: Int) {
    val position: Vector3
    private val velocity: Vector3

    val bird: Texture

    init {
        position = Vector3(x.toFloat(), y.toFloat(), 0f)
        velocity = Vector3(0f, 0f, 0f)
        bird = Texture("bird.png")
    }

    fun update(dt: Float) {
        if (position.y > 0)
             velocity.add(0f, GRAVITY.toFloat(), 0f)
             velocity.scl(dt)
             position.add(MOVEMENT*dt, velocity.y, 0f)
        if(position.y < 0)
            position.y = 0f

        velocity.scl(1 / dt)
    }

    companion object {
        private val MOVEMENT = 100
        private val GRAVITY = -15
    }

    fun jump(){
        velocity.y = 800f;
    }
}
