package com.fraktalizator.flood.ashley.actions.pathfinding

import com.badlogic.ashley.core.Entity
import com.fraktalizator.flood.EntityAction
import com.fraktalizator.flood.ashley.componentes.EntityTargetComponent
import com.fraktalizator.flood.ashley.componentes.MoveComponent
import com.fraktalizator.flood.ashley.componentes.PathfindingComponent
import com.fraktalizator.flood.ashley.componentes.PositionComponent
import com.fraktalizator.flood.ashley.entities.MoveTile
import com.fraktalizator.flood.extension_methods.get
import com.fraktalizator.flood.pathfinding.logic.DirectionPath
import com.fraktalizator.flood.pathfinding.logic.IPathfindingAlgorithm
import com.fraktalizator.flood.pathfinding.logic.ImmutableTile

class EntityPathfindingAction(
    private val pathfinding: IPathfindingAlgorithm
) : EntityAction() {
    override fun act(entity: Entity) {
        MoveTilePool.engine = engine!!
        val patCom = entity.get<PathfindingComponent>() ?: return
        val posCom = entity.get<PositionComponent>() ?: return

        if (entity.get<MoveComponent>()?.isMoving == true) return

        disposeOldMoveTiles()

        val tilesAndMoveCosts = pathfinding.flood(posCom.immutableTile(), patCom.range)
        val tilesInRange: List<ImmutableTile> = pathfinding.getTilesInRange(posCom.immutableTile(), patCom.range)
        val targetComponent = EntityTargetComponent(entity)

        for (tile in tilesInRange) {
            if (tile == posCom.immutableTile()) continue
            generateMoveTile(tile, pathfinding.getPath(posCom.immutableTile(), tile, tilesAndMoveCosts))
                .add(targetComponent)
        }
    }

    private fun generateMoveTile(immutableTile: ImmutableTile, directionPath: DirectionPath): MoveTile {
        val moveTile = MoveTilePool.obtainFresh()
        moveTile.init(immutableTile, directionPath)
        return moveTile
    }

    private fun disposeOldMoveTiles() {
        MoveTilePool.reset()
    }
}
