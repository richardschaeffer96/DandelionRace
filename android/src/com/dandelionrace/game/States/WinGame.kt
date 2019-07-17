package com.dandelionrace.game.States

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.dandelionrace
import com.dandelionrace.game.lobby.EntryHallActivity
import com.dandelionrace.game.sprites.Bird

class WinGame(gsm: GameStateManager, winner: Boolean) : State(gsm) {

    private val win: Texture
    val app_width: Float
    val app_height: Float
    private val playBtn: Texture

    private val bird: Bird

    init {
        if (winner){
            win = Texture("win.jpg")
        }else{
            win = Texture("win.jpg")
        }
        bird = Bird(100,500, 0)
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()
        playBtn = Texture("playBtn.png")
    }
    override fun update(dt: Float) {
        bird.update(dt)

        cam.position.set(bird.position.x + 80, cam.viewportHeight/2,0f)
        cam.update()
        handleInput()

    }


    override fun render(sb: SpriteBatch) {
        sb.projectionMatrix.set(cam.combined)
        sb.begin()
        sb.draw(win, cam.position.x - (cam.viewportWidth / 2 ), 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())
        sb.draw(playBtn, cam.position.x - (cam.viewportWidth / 4 - playBtn.width / 2).toFloat(), (app_height / 2).toFloat())
        sb.end()




    }

    override fun dispose() {
        win.dispose()
        playBtn.dispose()
    }


    override fun handleInput() {
        if (Gdx.input.justTouched()) {
            //gsm.set(MenuState(gsm))
            dispose();
        }
    }

}