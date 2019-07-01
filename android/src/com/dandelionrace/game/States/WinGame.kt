package com.dandelionrace.game.States

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch

class WinGame(gsm: GameStateManager) : State(gsm) {

    private val win: Texture
    val app_width: Float
    val app_height: Float

    init {
        win = Texture("win.jpg")
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        sb.draw(win, 0f, 0f, app_width, app_height)
        sb.end()
    }

    override fun dispose() {
        win.dispose()
    }

    override fun update(dt: Float) {

    }

    override fun handleInput() {

    }


}