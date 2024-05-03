package com.fraktalizator.flood.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.utils.Pathfinding

class PathToTileComponent(pathToTile: ArrayList<Pathfinding.Direction>) : Component {
    var pathToTile: ArrayList<Pathfinding.Direction> = pathToTile
        private set
}
