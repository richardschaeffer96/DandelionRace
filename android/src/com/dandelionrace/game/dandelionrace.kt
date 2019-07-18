package com.dandelionrace.game

import android.content.Context
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dandelionrace.game.States.GameStateManager
import com.dandelionrace.game.States.PlayState
import com.dandelionrace.game.sprites.GameItems
import com.dandelionrace.game.sprites.GameTubes
import com.google.firebase.database.FirebaseDatabase


class dandelionrace(mContext: Context, tubeString: String, itemString: String, gameName: String, enemy: String, screenWidth: Int, screenHeight: Int) : ApplicationAdapter() {
    internal lateinit var gsm: GameStateManager
    internal lateinit var batch: SpriteBatch
    val game_Context: Context = mContext
    val tubesString: String = tubeString
    val itemsString: String = itemString
    val gameName = gameName
    val enemy = enemy
    val database = FirebaseDatabase.getInstance()
    val screenWidth: Int = screenWidth
    val screenHeight: Int = screenHeight

    override fun create() {

        // TODO: Check why screensize is buggy
        //WIDTH = screenWidth
        //HEIGHT = screenHeight
        batch = SpriteBatch()
        gsm = GameStateManager()
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)

        var finalItems: ArrayList<GameItems>
        finalItems = ArrayList<GameItems>()

        var items = itemsString.split("%")

        for(i in items){
            if (i==items[items.size-1])
            {

            } else {
                var items2: List<String> = i.split("$")
                finalItems.add(GameItems(items2[0].toFloat(), items2[1].toFloat(), items2[2]))
            }

        }

        var finalTubes: ArrayList<GameTubes>
        finalTubes = ArrayList<GameTubes>()

        var tubes = tubesString.split("%")

        for(t in tubes){
            if (t==tubes[tubes.size-1])
            {

            } else {
                var tubes2: List<String> = t.split("$")
                finalTubes.add(GameTubes(tubes2[0].toFloat(), tubes2[1].toFloat(), tubes2[2].toFloat(), tubes2[3].toFloat()))
            }

        }
        println("DR: "+ enemy)

        finalTubes = ArrayList<GameTubes>()
        finalTubes.add(GameTubes(20f,20f,20f,20f))

        gsm!!.push(PlayState(gsm, finalTubes, finalItems, gameName, enemy))

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
        lateinit var batch: SpriteBatch

        //TO-DO: Get screen sizes of device!!!
        var WIDTH: Int = 1080  //480;
        var HEIGHT = 2240
        val SCALE = 0.5f
        val TITLE = "Dandelion Race"
    }


}
