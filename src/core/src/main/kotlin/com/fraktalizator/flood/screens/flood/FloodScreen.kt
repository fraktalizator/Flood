package com.fraktalizator.flood.screens.flood

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.fraktalizator.flood.game_objects.EntityInputManager
import com.fraktalizator.flood.game_objects.GameWorld
import com.fraktalizator.flood.Flood
import ktx.app.KtxScreen

class FloodScreen(private val gameWorld: GameWorld) : KtxScreen{
    internal val camera = OrthographicCamera()
    private val viewport = ExtendViewport(Flood.VIEWPORT_WIDTH, Flood.VIEWPORT_HEIGHT, camera)
    private val stage = Stage(viewport, Flood.mainBatch)
    val engine = gameWorld.engine
    private val entityInputManager = EntityInputManager(viewport, gameWorld)

    override fun show() {
        Gdx.input.inputProcessor = entityInputManager
        gameWorld.mapRenderer.setView(camera)
        //camera.zoom = 0.2f
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        camera.update()
        gameWorld.mapRenderer.setView(camera)
        gameWorld.mapRenderer.render()
        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.translate(Vector2(0f, 1f))
        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.translate(Vector2(0f, -1f))
        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.translate(Vector2(-1f, 0f))
        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.translate(Vector2(1f, 0f))

        engine.update(delta)
//        Main.mainBatch.begin()
//
//        Main.mainBatch.end()

        stage.act(delta)
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        stage.viewport.setScreenSize(width, height)
        stage.camera.update(true)
    }


    override fun dispose() {
        stage.dispose()
    }
}
