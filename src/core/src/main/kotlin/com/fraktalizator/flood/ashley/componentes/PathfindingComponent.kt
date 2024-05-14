package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.pathfinding.MoveType

data class PathfindingComponent(
    var range: Int,
    var type: MoveType
) : Component
