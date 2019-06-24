package com.dandelionrace.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.dandelionrace.game.dandelionrace

object DesktopLauncher {

    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.height = dandelionrace.HEIGHT;
        config.width = dandelionrace.WIDTH;
        config.title = dandelionrace.TITLE;
        LwjglApplication(dandelionrace(), config)
    }
}
