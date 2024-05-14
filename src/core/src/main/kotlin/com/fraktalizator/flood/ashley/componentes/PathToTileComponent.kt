package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.pathfinding.logic.DirectionPath

class PathToTileComponent(pathToTile: DirectionPath) :
    Component {
    var pathToTile: DirectionPath = pathToTile
        private set
}
