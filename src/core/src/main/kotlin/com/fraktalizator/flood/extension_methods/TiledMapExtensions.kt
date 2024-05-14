package com.fraktalizator.flood.extension_methods

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.fraktalizator.flood.pathfinding.logic.ImmutableTile

fun TiledMap.getMoveCost(tile: ImmutableTile): Int {
    val cell = (layers.get(0) as TiledMapTileLayer).getCell(tile.x(), tile.y())
    return cell?.tile?.properties?.get("moveCost")?.toString()?.toInt() ?: 9999
}
