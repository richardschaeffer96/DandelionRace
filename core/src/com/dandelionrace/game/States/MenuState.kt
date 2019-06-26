package com.dandelionrace.game.States

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.dandelionrace
import sun.rmi.runtime.Log

class MenuState(gsm: GameStateManager) : State(gsm) {

    private val background: Texture
    private val playBtn: Texture
    val app_width: Float
    val app_height: Float

    init {
        background = Texture("bg.png")
        playBtn = Texture("playBtn.png")
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()
    }

    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(PlayState(gsm))
            dispose();
        }
    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        sb.draw(background, 0f, 0f, app_width, app_height)
        sb.draw(playBtn, (app_width / 2 - playBtn.width / 2).toFloat(), (app_height / 2).toFloat())
        println("I DREW IT")
        sb.end()
    }

    override fun dispose() {
       background.dispose()
       playBtn.dispose()
    }
}
