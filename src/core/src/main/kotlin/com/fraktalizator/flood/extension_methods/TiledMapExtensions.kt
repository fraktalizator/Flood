package com.fraktalizator.flood.extension_methods

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent.Companion.GRID_SIZE

object TiledMapExtensions {
    fun TiledMap.getMoveCost(cellPosition: Vector2): Int {
        val cell = (layers.get(0) as TiledMapTileLayer).getCell(
            cellPosition.x.toInt() / GRID_SIZE.toInt(),
            cellPosition.y.toInt() / GRID_SIZE.toInt()
        )
        return cell?.tile?.properties?.get("moveCost")?.toString()?.toInt() ?: 9999
    }
}
