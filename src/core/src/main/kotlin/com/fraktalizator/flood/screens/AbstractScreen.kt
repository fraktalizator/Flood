package com.fraktalizator.flood.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.configs.Assets
import ktx.app.KtxScreen

abstract class AbstractScreen : KtxScreen {
    val stage: Stage = Stage(ExtendViewport(Flood.VIEWPORT_HEIGHT, Flood.VIEWPORT_WIDTH), bach)
    companion object {
        const val enableSceneDebug = false
        val clearColor = Color(0f, 0f, 0f, 1f)
        val skin: Skin = Assets.skin
        val game: Flood = Flood
        val bach:SpriteBatch = Flood.mainBatch
    }

    init{
        if (enableSceneDebug) {
            stage.setDebugUnderMouse(true)
            stage.setDebugTableUnderMouse(true)
            stage.setDebugParentUnderMouse(true)
        }
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        update(delta)

        stage.act()
        stage.draw()
    }

    open fun update(delta: Float){}

    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, false)
    }

    fun isPortrait() = stage.viewport.screenHeight > stage.viewport.screenWidth

    fun getScreenCenter() = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat()).scl(1/2f)

    fun getScreenCenterFor(width: Float, height: Float) = getScreenCenterFor(Vector2(width, height))
    fun getScreenCenterFor(objectSize:Vector2) = getScreenCenter().add(objectSize.scl(1/2f).scl(-1f))

    override fun dispose() = stage.dispose()

}
