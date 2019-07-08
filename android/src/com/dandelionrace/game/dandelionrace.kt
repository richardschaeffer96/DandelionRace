package com.dandelionrace.game

import android.content.Context
import android.widget.Button
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.States.GameStateManager
import com.dandelionrace.game.States.MenuState
import com.dandelionrace.game.States.PlayState
import com.dandelionrace.game.sprites.GameTubes


class dandelionrace(mContext: Context, tubeString: String, gameName: String) : ApplicationAdapter() {
    internal lateinit var gsm: GameStateManager
    internal lateinit var batch: SpriteBatch
    val game_Context: Context = mContext
    val tubesString: String = tubeString
    val gameName = gameName


    override fun create() {

        batch = SpriteBatch()
        gsm = GameStateManager()
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)


        var finalTubes: ArrayList<GameTubes>
        finalTubes = ArrayList<GameTubes>()

        var tubes = tubesString.split("%")

        for(t in tubes){
            if (t==tubes[tubes.size-1])
            {

            } else {
                var tubes2: List<String> = t.split("$")
                System.out.println("ERSTE LISTE: " + tubes)
                System.out.println("ZWEITE LISTE: " + tubes2)
                System.out.println("AUSGABE: "+ tubes2[0])
                System.out.println("AUSGABE: "+ tubes2[1])
                System.out.println("AUSGABE: "+ tubes2[2])
                System.out.println("AUSGABE: "+ tubes2[3])
                finalTubes.add(GameTubes(tubes2[0].toFloat(), tubes2[1].toFloat(), tubes2[2].toFloat(), tubes2[3].toFloat()))
            }

        }

        gsm!!.push(PlayState(gsm, finalTubes, gameName))

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
        val WIDTH =  1080  //480;
        val HEIGHT = 2240
        val SCALE = 0.5f
        val TITLE = "Dandelion Race"
    }
}
