package com.fraktalizator.flood.entityEngine.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.pathfinding_alghoritms.Direction

class PathToTileComponent(pathToTile: ArrayList<Direction>) : Component {
    var pathToTile: ArrayList<Direction> = pathToTile
        private set
}
