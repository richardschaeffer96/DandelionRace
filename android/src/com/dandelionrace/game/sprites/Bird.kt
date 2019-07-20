package com.dandelionrace.game.sprites

import android.media.Image
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.dandelionrace.game.dandelionrace

class Bird(x: Int, y: Int, player: Int) {
    var position: Vector3
    private val velocity: Vector3
    private val bound: Rectangle
    var status: String = "free"
    var trappedTube: String = ""
    lateinit var birdAnimation: Animations
    val bird: Texture
    var maxY: Float
    var move: Int = 150

    init {

        //TODO: MAKE MAXY TO MAX Y VALUE OF THE SCREEN! AUTOMATICALLY!
        maxY = 1900f
        status = "free"
        trappedTube = ""
        position = Vector3(x.toFloat(), y.toFloat(), 0f)
        velocity = Vector3(0f, 0f, 0f)
        if(player==1) {
            bird = Texture("bugredanimation.png")
            birdAnimation = Animations(TextureRegion(bird), 2, 0.5f)
        }else{
            bird = Texture("bugyellowanimation.png")
            birdAnimation = Animations(TextureRegion(bird), 2, 0.5f)
        }
        bound = Rectangle(x.toFloat(), y.toFloat(), bird.width.toFloat()-200, bird.height.toFloat())
    }

    fun update(dt: Float) {
        birdAnimation.update(dt)
        if(status=="free") {
            if (position.y > 0 && position.y < maxY)
                velocity.add(0f, GRAVITY.toFloat(), 0f)
            velocity.scl(dt)
            position.add(move * dt, velocity.y, 0f)
            if (position.y < 0)
                position.y = 0f
            if (position.y >= maxY){
                position.y = maxY-1f
            }

            velocity.scl(1 / dt)
            bound.setPosition(position.x, position.y)
            }else if(status=="trapped"){

        }

    }

    companion object {

        private val MOVEMENT = 100
        private val GRAVITY = -15
    }

    fun jump(){
        if (status=="free"||status=="SLOW"||status=="SPEED")
            velocity.y = 500f;
    }

    fun bushjump(){
        if (status=="free"||status=="SLOW"||status=="SPEED")
            velocity.y = 800f;
            position.x += 10
            position.y += 70
    }

    fun invertjump(){
        if (status=="free"||status=="SLOW"||status=="SPEED")
            velocity.y = -800f;
    }

    fun getBound():Rectangle{
        return bound
    }
}
