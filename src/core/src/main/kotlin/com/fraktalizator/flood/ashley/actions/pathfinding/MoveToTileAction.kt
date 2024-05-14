package com.fraktalizator.flood.ashley.actions.pathfinding

import com.badlogic.ashley.core.Entity
import com.fraktalizator.flood.EntityAction
import com.fraktalizator.flood.ashley.componentes.EntityTargetComponent
import com.fraktalizator.flood.ashley.componentes.MoveComponent
import com.fraktalizator.flood.ashley.componentes.PathToTileComponent
import com.fraktalizator.flood.ashley.entities.MoveTile
import ktx.ashley.get

class MoveToTileAction : EntityAction() {
    override fun act(entity: Entity) {
        if (entity !is MoveTile) return
        if (entity.get<EntityTargetComponent>() == null) return
        if (entity.get<EntityTargetComponent>()?.target == null) return

        val targetEntity = entity.get<EntityTargetComponent>()!!.target!!
        val entityMoveCom = targetEntity.get<MoveComponent>()!!

        entityMoveCom.path = entity.get<PathToTileComponent>()!!.pathToTile
        entityMoveCom.isMoving = true
        println("start moving: " + entityMoveCom.path)
        MoveTilePool.reset()

    }
}
