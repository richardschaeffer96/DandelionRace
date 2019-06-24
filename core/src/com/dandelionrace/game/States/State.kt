package com.dandelionrace.game.States

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3

abstract class State protected constructor(protected var gsm: GameStateManager) {

    protected var cam: OrthographicCamera

    protected var mouse: Vector3

    init {
        cam = OrthographicCamera()
        mouse = Vector3()


    }

    abstract fun handleInput()
    abstract fun update(dt: Float)
    abstract fun render(sb: SpriteBatch)
    abstract fun dispose()
}
