package com.fraktalizator.flood.screens.flood

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.entityEngine.WorldEngineInitializer
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent
import com.fraktalizator.flood.entityEngine.systems.TiledMapRenderSystem
import com.fraktalizator.flood.screens.BaseScreen
import com.fraktalizator.flood.ui.SettingsShowButton
import com.fraktalizator.flood.ui.settings.SettingsWindow
import ktx.ashley.getSystem

class FloodScreen(
    private val worldEngineInitializer: WorldEngineInitializer,
    private val settingsWindow: SettingsWindow
) : BaseScreen() {
    internal val engine = worldEngineInitializer.engine

    //private val entityInputManager = EntityInputManager(viewport, worldEngineInitializer)
    private val cameraSpeed = PositionComponent.GRIDSIZE * 25

    override fun show() {
        //Gdx.input.inputProcessor = entityInputManager

        stage.addActor(settingsWindow)
        val settingsShowButton = SettingsShowButton(settingsWindow)
        settingsShowButton.setPosition(100f, 100f)
        stage.addActor(settingsShowButton)
    }

    override fun update(delta: Float) {
        engine.update(delta)
        translateCamera(delta)
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
