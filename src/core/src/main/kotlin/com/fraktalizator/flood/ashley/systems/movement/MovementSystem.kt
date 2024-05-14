package com.fraktalizator.flood.ashley.systems.movement

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.fraktalizator.flood.ashley.componentes.MoveComponent
import com.fraktalizator.flood.ashley.componentes.PositionComponent
import com.fraktalizator.flood.extension_methods.plus
import com.fraktalizator.flood.pathfinding.logic.Tile
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.abs

class MovementSystem : IteratingSystem(
    allOf(MoveComponent::class, PositionComponent::class).get()
) {
    companion object {
        private const val SPEED = Tile.SIZE * 8 // means 8 tiles per sec
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        if (entity == null) return

        val moveCom = entity.get<MoveComponent>()!!
        if (!moveCom.isMoving) return

        val posCom = entity.get<PositionComponent>()!!

        if (moveCom.path.isEnded()) {
            moveCom.isMoving = false
            return
        }

        if (!moveCom.path.isStarted()) {
            moveCom.path.start()
            moveCom.startPosition = posCom.position
        }
        val startPosition = moveCom.getCurrentStartTilePosition()
        println("Moving ${moveCom.path}")
        posCom.position += moveCom.path
            .getDirection()
            .tile
            .toVector()
            .scl(deltaTime)
            .scl(SPEED)

        val traveledDistance = abs(posCom.position.x - startPosition.x + posCom.position.y - startPosition.y)
        if (traveledDistance >= Tile.SIZE) {
            moveCom.path.directionReached()
            posCom.position = moveCom.getCurrentStartTilePosition()
        }
    }

}
