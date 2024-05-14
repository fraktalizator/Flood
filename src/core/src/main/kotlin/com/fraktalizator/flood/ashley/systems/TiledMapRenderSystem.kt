package com.fraktalizator.flood.ashley.systems

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.fraktalizator.flood.Flood

class TiledMapRenderSystem(private val tiledMap: TiledMap) : EntitySystem() {
    private val mapRenderer = OrthogonalTiledMapRenderer(tiledMap, 1f, Flood.mainBatch)
    val camera = OrthographicCamera(Flood.VIEWPORT_WIDTH, Flood.VIEWPORT_HEIGHT)

    override fun update(deltaTime: Float) {
        camera.update()
        mapRenderer.setView(camera)
        mapRenderer.render()
    }

    fun dispose() {
        mapRenderer.dispose()
        tiledMap.dispose()
    }
}
