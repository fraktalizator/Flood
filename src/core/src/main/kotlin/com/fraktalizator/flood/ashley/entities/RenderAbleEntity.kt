package com.fraktalizator.flood.ashley.entities

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.ashley.componentes.PositionComponent
import com.fraktalizator.flood.ashley.componentes.RenderComponent
import com.fraktalizator.flood.pathfinding.logic.Tile

open class RenderAbleEntity(
    position: Vector2,
    texture: Texture?,
    animate: Boolean
) : Entity() {
    init {
        if (animate) {
            val positionComponent = PositionComponent(position.x, position.y, 32f, 48f)
            this.add(positionComponent)
            this.add(RenderComponent(texture, 1000 - positionComponent.tile().y.toInt(), 32f, 48f))
            //TODO z index zeby chlopki nie mialy wyzej nogi niz glowy ^
        } else {
            this.add(PositionComponent(position.x, position.y, Tile.SIZE, Tile.SIZE))
            this.add(RenderComponent(texture, 0))
        }
    }
}
