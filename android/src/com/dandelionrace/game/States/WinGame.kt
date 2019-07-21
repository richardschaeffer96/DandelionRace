package com.dandelionrace.game.States

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.lobby.EntryHallActivity
import com.dandelionrace.game.sprites.Bird
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ImageView
import com.badlogic.gdx.graphics.g3d.particles.ParticleSorter
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import android.R.attr.button
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.Drawable
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import android.R.attr.button
import android.util.EventLog
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.dandelionrace.game.*
import java.util.*


class WinGame(gsm: GameStateManager, winner: Boolean) : State(gsm) {

    private val win: Texture
    val app_width: Float
    val app_height: Float
    private val playButton: ImageButton
    private val stage: Stage

    private val bird: Bird

    init {

        if (winner){
            win = Texture("winscreen.jpg")
        }else{
            win = Texture("lostscreen.jpg")
        }
        bird = Bird(100,500, 0)
        app_height = Gdx.app.graphics.height.toFloat()
        app_width = Gdx.app.graphics.width.toFloat()

        stage = Stage()
        Gdx.input.inputProcessor = stage
        val drawable = TextureRegionDrawable (TextureRegion(Texture("back.png")))
        playButton = ImageButton(drawable);
        stage.addActor(playButton);

        playButton.addListener(object : ClickListener() {
            override  fun clicked(event: InputEvent, x: Float, y: Float) {
                Gdx.app.exit()
            }
        })
    }
    override fun update(dt: Float) {

    }


    override fun render(sb: SpriteBatch) {
        sb.projectionMatrix.set(cam.combined)
        sb.begin()
        sb.draw(win, cam.position.x - (cam.viewportWidth / 2 ), 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())
        sb.end()
        playButton.x = cam.position.x - ((cam.viewportWidth / 2) - playButton.width)
        playButton.y = (app_height / 2) -400

        stage.draw()

    }

    override fun dispose() {
        win.dispose()
    }


    override fun handleInput() {
        if (Gdx.input.justTouched()) {
           //dispose();
        }
    }

}