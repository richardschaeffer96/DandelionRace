package com.dandelionrace.game.States

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.dandelionrace
import com.dandelionrace.game.sprites.Bird
import com.dandelionrace.game.sprites.Tube

class PlayState(gsm: GameStateManager) : State(gsm) {

    private val TUBE_SPACING: Float = 125f
    private val TUBE_COUNT: Int = 4
    private val bird: Bird
    private val bg: Texture

    private val tubes: ArrayList<Tube>

    init {
        bird = Bird(100,500)
        bg = Texture("bg.png")
        tubes = ArrayList<Tube>()
        for (i in 1..TUBE_COUNT) {
            //vielleicht kleiner als noch setzen
            tubes.add(Tube(i*(TUBE_SPACING+Tube.TUBE_WIDTH)))
        }
    }

    override fun handleInput() {
        if(Gdx.input.justTouched())
            bird.jump()
    }

    override fun update(dt: Float) {
        handleInput()
        bird.update(dt)
        cam.position.set(bird.position.x + 80, cam.viewportHeight/2,0f)
        for(tube in tubes){
            if(cam.position.x - (cam.viewportWidth/2) > tube.posTopTube.x + tube.topTube.width)
                tube.reposition(tube.posTopTube.x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT))
        }

        cam.update()
    }

    override fun render(sb: SpriteBatch) {
        //sb.projectionMatrix.set(cam.combined)
        sb.begin()
        sb.draw(bg, cam.position.x - (cam.viewportWidth / 2 ), 0f)

        for(tube in tubes) {
            sb.draw(tube.topTube, tube.posTopTube.x, tube.posTopTube.y)
            sb.draw(tube.bottomTube, tube.posBotTube.x, tube.posBotTube.y)
        }
        //sb.draw(bg, 0f, 0f, dandelionrace.WIDTH.toFloat(), dandelionrace.HEIGHT.toFloat())
        sb.draw(bird.bird, bird.position.x, bird.position.y)
        //sb.draw(tube.topTube, tube.posTopTube.x, tube.posTopTube.y)
        //sb.draw(tube.bottomTube, tube.posBotTube.x, tube.posBotTube.y)
        sb.end()
    }

    override fun dispose() {

    }
}
