package com.fraktalizator.flood.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.ScalingViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.extension_methods.minus
import com.fraktalizator.flood.extension_methods.times
import ktx.app.KtxScreen

abstract class BaseScreen<T : Stage>(
    stageConstructor: (Viewport, Batch) -> T
)  : KtxScreen {
    val stage: Stage = stageConstructor.invoke(ScalingViewport(Scaling.stretch, Flood.VIEWPORT_WIDTH, Flood.VIEWPORT_HEIGHT), bach)
    val camera: OrthographicCamera = stage.camera as OrthographicCamera
    val viewport: ScalingViewport = stage.viewport as ScalingViewport

    companion object {
        const val enableSceneDebug = true
        val clearColor = Color(0f, 0f, 0f, 1f)
        val skin: Skin = Assets.skin
        val game: Flood = Flood
        val bach: SpriteBatch = Flood.mainBatch
    }

    init {
        if (enableSceneDebug) {
            stage.setDebugUnderMouse(true)
            stage.setDebugTableUnderMouse(true)
            stage.setDebugParentUnderMouse(true)
        }
        //camera.setToOrtho(true, viewport.worldWidth, viewport.worldHeight)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(clearColor.r, clearColor.g, clearColor.b, clearColor.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        update(delta)

        stage.batch.begin()
        draw(delta)
        stage.batch.end()

        stage.act()
        stage.draw()
    }

    open fun update(delta: Float) {}
    open fun draw(delta: Float) {}

    @Suppress("NAME_SHADOWING")
    override fun resize(width: Int, height: Int) {
        val height = (width*Flood.VIEWPORT_HEIGHT/Flood.VIEWPORT_WIDTH).toInt()
        Gdx.app.log("BaseScreen (${this.javaClass.simpleName})", "settings screen size to $width x $height")
        Gdx.graphics.setWindowedMode(width, height)
        viewport.update(width, height, false)
        camera.update()//TODO is this line necessary
    }

    fun isPortrait() = stage.viewport.screenHeight > stage.viewport.screenWidth

    fun getScreenSizeInPx() = stage.screenToStageCoordinates(Vector2(Gdx.graphics.width.toFloat(), 0f))

    fun getScreenCenterFor(width: Float, height: Float) = getScreenCenterFor(Vector2(width, height))
    fun getScreenCenterFor(objectSize: Vector2) = getScreenSizeInPx() * (1/2f) - (Vector2(objectSize))* (1/2f)

    override fun dispose() = stage.dispose()

}
