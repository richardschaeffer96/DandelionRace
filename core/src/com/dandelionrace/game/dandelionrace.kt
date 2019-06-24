package com.dandelionrace.game

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.States.GameStateManager
import com.dandelionrace.game.States.MenuState

class dandelionrace : ApplicationAdapter() {
    internal lateinit var gsm: GameStateManager
    internal lateinit var batch: SpriteBatch


    override fun create() {

        batch = SpriteBatch()
        gsm = GameStateManager()
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        gsm!!.push(MenuState(gsm))
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gsm!!.update(Gdx.graphics.deltaTime)
        gsm!!.render(batch)
    }

    override fun dispose() {
        batch!!.dispose()
    }

    companion object {
        //TO-DO: Get screen sizes of device!!!
        val WIDTH = 1080  //480;
        val HEIGHT = 2240

        val TITLE = "Dandelion Race"
    }
}
