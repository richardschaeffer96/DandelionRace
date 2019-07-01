package com.dandelionrace.game.States

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.dandelionrace.game.States.GameStateManager

abstract class State protected constructor(protected var gsm: GameStateManager) {

    protected var cam: OrthographicCamera

    protected var mouse: Vector3

    init {
        cam = OrthographicCamera()
        cam.setToOrtho(false, 1080f, 2240f)
        mouse = Vector3()


    }

    abstract fun handleInput()
    abstract fun update(dt: Float)
    abstract fun render(sb: SpriteBatch)
    abstract fun dispose()
}
