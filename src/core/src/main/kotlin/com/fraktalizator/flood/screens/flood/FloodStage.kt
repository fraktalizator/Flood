package com.fraktalizator.flood.screens.flood

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport

class FloodStage(viewport: Viewport, batch: Batch) : Stage(viewport, batch) {
    private var tempCoords:Vector2 = Vector2.Zero

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        val ret = super.touchDown(screenX, screenY, pointer, button)
        screenToStageCoordinates(tempCoords.set(screenX.toFloat(), screenY.toFloat()))

        val target = hit(tempCoords.x, tempCoords.y, true)
        println("hit $target at ${tempCoords}")
        return ret
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        val ret =  super.mouseMoved(screenX, screenY)
        screenToStageCoordinates(tempCoords.set(screenX.toFloat(), screenY.toFloat()))

        val target = hit(tempCoords.x, tempCoords.y, true)
        println("hit $target at ${tempCoords}")
        return ret
    }
}
