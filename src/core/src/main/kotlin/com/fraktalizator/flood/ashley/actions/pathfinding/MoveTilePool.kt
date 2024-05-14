package com.fraktalizator.flood.ashley.actions.pathfinding

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.utils.Array
import com.fraktalizator.flood.ashley.componentes.PositionComponent
import com.fraktalizator.flood.ashley.entities.MoveTile
import com.fraktalizator.flood.pathfinding.logic.DirectionPath
import com.fraktalizator.flood.pathfinding.logic.ImmutableTile
import ktx.ashley.get


object MoveTilePool {

    lateinit var engine: Engine
    private val objects: Array<MoveTile> = Array(false, 16 * 16)
    var positionMap: Map<ImmutableTile, MoveTile>? = null
        private set
        get() {
            if (field == null) {
                field = objects.items.toList()
                    .filter { it?.get<PositionComponent>() != null }
                    .associateBy({ it.get<PositionComponent>()!!.immutableTile() }, { it })
            }
            return field
        }

    private fun getNewMoveTile(): MoveTile = MoveTile()

    fun obtainFresh(): MoveTile {
        objects.forEach {
            if (!it.isInitialized) return it
        }
        val newMoveTile = getNewMoveTile()
        objects.add(newMoveTile)
        engine.addEntity(newMoveTile)
        return newMoveTile
    }

    fun reset() {
        objects.forEach { it.reset() }
        positionMap = null
    }

    fun dispose() {
        reset()
        objects.clear()
        positionMap = null
        objects.forEach { engine.removeEntity(it) }
    }

    fun getMoveTilesByPath(moveTile: MoveTile, directionPath: DirectionPath): List<MoveTile> {
        val moveTileList = ArrayList<MoveTile>(10)
        moveTileList.add(moveTile)
        val directionPath = DirectionPath.reverse(directionPath)
        for (index in 0..<directionPath.length() - 1) {
            val direction = directionPath[index]
            val positionOfNextTile =
                moveTileList[moveTileList.size - 1].get<PositionComponent>()!!.immutableTile() + direction.tile
            moveTileList.add(positionMap!![positionOfNextTile]!!)
        }
        moveTileList.remove(moveTile)
        return moveTileList
    }

    operator fun iterator(): Iterator<MoveTile> {
        return objects.iterator()
    }

    fun unselectTiles() {
        for (moveTile in MoveTilePool) {
            if (moveTile.isInitialized) {
                moveTile.unSelect()
            }
        }
    }


}
