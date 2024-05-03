package com.fraktalizator.flood.screens.flood

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.componentes.PositionComponent
import com.fraktalizator.flood.game_objects.EntityInputManager
import com.fraktalizator.flood.game_objects.GameWorld
import com.fraktalizator.flood.screens.BaseScreen

class FloodScreen(private val gameWorld: GameWorld) : BaseScreen(){
    internal val engine = gameWorld.engine
    private val entityInputManager = EntityInputManager(viewport, gameWorld)
    private val cameraSpeed = PositionComponent.GRIDSIZE*25

    override fun show() {
        Gdx.input.inputProcessor = entityInputManager
        gameWorld.mapRenderer.setView(camera)
        camera.zoom = 0.5f
    }

    override fun update(delta: Float) {
        gameWorld.mapRenderer.setView(camera)
        gameWorld.mapRenderer.render()

        engine.update(delta)

        translateCamera(delta)
    }

    fun translateCamera(delta: Float){
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.translate(Vector2(0f, cameraSpeed).scl(delta))
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.translate(Vector2(0f, -cameraSpeed).scl(delta))
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.translate(Vector2(-cameraSpeed, 0f).scl(delta))
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.translate(Vector2(cameraSpeed, 0f).scl(delta))
    }
}
