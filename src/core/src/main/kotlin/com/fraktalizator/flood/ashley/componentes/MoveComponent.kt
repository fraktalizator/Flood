package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.extension_methods.plus
import com.fraktalizator.flood.pathfinding.logic.DirectionPath

data class MoveComponent(
    var path: DirectionPath,
    var isMoving: Boolean = false,
) : Component {
    var startPosition: Vector2 = Vector2()
        set(value) {
            field = value; }//roundToTile

    fun getCurrentStartTilePosition(): Vector2 = Vector2(path.getWalkedVector()) + startPosition
}

