package com.dandelionrace.game.sprites

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Array

class Animations(region: TextureRegion, frameCount: Int, cycleTime: Float) {
    val frames: Array<TextureRegion>
    private var maxFrameTime: Float = 0.toFloat()
    private var currentFrameTime: Float = 0.toFloat()
    private var frameCount: Int = 0
    private var frame: Int = 0

    init {
        frames = Array()
        val frameWidth = region.regionWidth / frameCount
        for (i in 0 until frameCount) {
            frames.add(TextureRegion(region, i * frameWidth, 0, frameWidth, region.regionHeight))
        }
        this.frameCount = frameCount
        maxFrameTime = cycleTime / frameCount
        frame = 0

    }

    fun update(dt: Float) {
        currentFrameTime += dt
        if (currentFrameTime > maxFrameTime) {
            frame++
            currentFrameTime = 0f
        }
        if (frame >= frameCount) {
            frame = 0
        }
    }

    fun getFrame(): TextureRegion{
        return frames.get(frame)
    }

}
