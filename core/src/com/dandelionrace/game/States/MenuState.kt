package com.dandelionrace.game.States

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.dandelionrace
import sun.rmi.runtime.Log

class MenuState(gsm: GameStateManager) : State(gsm) {

    private val background: Texture
    private val playBtn: Texture

    init {
        background = Texture("bg.png")
        playBtn = Texture("playBtn.png")
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
        sb.draw(background, 0f, 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())
        sb.draw(playBtn, (dandelionrace.WIDTH / 2 - playBtn.width / 2).toFloat(), (dandelionrace.HEIGHT / 2).toFloat())
        println("I DREW IT")
        sb.end()
    }

    override fun dispose() {
       background.dispose()
       playBtn.dispose()
    }
}
