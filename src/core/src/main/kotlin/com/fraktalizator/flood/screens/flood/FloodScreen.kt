package com.fraktalizator.flood.screens.flood

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.ashley.WorldEngineInitializer
import com.fraktalizator.flood.ashley.systems.TiledMapRenderSystem
import com.fraktalizator.flood.pathfinding.logic.Tile
import com.fraktalizator.flood.screens.BaseScreen
import com.fraktalizator.flood.ui.SettingsShowButton
import com.fraktalizator.flood.ui.settings.SettingsWindow
import ktx.ashley.getSystem

class FloodScreen(
    private val worldEngineInitializer: WorldEngineInitializer,
    private val settingsWindow: SettingsWindow
) : BaseScreen<FloodStage>(::FloodStage) {
    private val engine = worldEngineInitializer.engine

    //private val entityInputManager = EntityInputManager(viewport, worldEngineInitializer)
    private val cameraSpeed = Tile.SIZE * 25

    override fun show() {
        Gdx.input.inputProcessor = stage
        println(getScreenSizeInPx())
        println(getScreenCenterFor(0f, 0f))
        println(getScreenCenterFor(Vector2(0f, 0f)))
        println("size "+ settingsWindow.size())
        val settingsWindowCenterPosition = getScreenCenterFor(settingsWindow.size())
        println(settingsWindowCenterPosition.x + settingsWindow.size().x)
        settingsWindow.setPosition(settingsWindowCenterPosition.x, settingsWindowCenterPosition.y)
        //settingsWindow.setPosition(getScreenSizeInPx().x, 100f)
        stage.addActor(settingsWindow)
        val settingsShowButton = SettingsShowButton(settingsWindow)
        settingsShowButton.setPosition(1024f-100, 500f)
        stage.addActor(settingsShowButton)
    }

    override fun update(delta: Float) {
        engine.update(delta)
        translateCamera(delta)
        //println("screen: ${getScreenSizeInPx()}, settingsScreen: ${settingsWindow.x} x ${settingsWindow.y} ")
    }

    private fun translateCamera(delta: Float) {
        val gameWorldCamera = engine.getSystem<TiledMapRenderSystem>().camera
        if (Gdx.input.isKeyPressed(Input.Keys.W)) gameWorldCamera.translate(Vector2(0f, cameraSpeed).scl(delta))
        if (Gdx.input.isKeyPressed(Input.Keys.S)) gameWorldCamera.translate(Vector2(0f, -cameraSpeed).scl(delta))
        if (Gdx.input.isKeyPressed(Input.Keys.A)) gameWorldCamera.translate(Vector2(-cameraSpeed, 0f).scl(delta))
        if (Gdx.input.isKeyPressed(Input.Keys.D)) gameWorldCamera.translate(Vector2(cameraSpeed, 0f).scl(delta))
    }

    override fun dispose() {
        engine.getSystem<TiledMapRenderSystem>().dispose()
    }
}
