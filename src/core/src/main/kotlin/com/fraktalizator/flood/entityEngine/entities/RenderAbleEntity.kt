package com.fraktalizator.flood.entityEngine.entities

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent.Companion.GRID_SIZE
import com.fraktalizator.flood.entityEngine.componentes.RenderComponent
import com.fraktalizator.flood.entityEngine.systems.MovementSystem
import com.fraktalizator.flood.screens.flood.FloodScreen

open class RenderAbleEntity(
    position: Vector2,
    texture: Texture?,
    animate: Boolean
) : Entity() {
    init {
        if (animate) {
            val positionComponent = PositionComponent(position.x, position.y, 32f, 48f)
            add(positionComponent)
            add(RenderComponent(texture, 1000-positionComponent.getTilePosition().y.toInt(), 32f, 48f))
        } else {
            add(PositionComponent(position.x, position.y, 32f, 32f))
            add(RenderComponent(texture, 0))
        }
    }

    fun setZIndexByPosition() {
        getComponent(RenderComponent::class.java).zindex = (zIndexByPosition)
        //getComponent(PositionComponent::class.java).getTilePosition().y.toInt()
    }

    private val zIndexByPosition: Int
        get() = getComponent(PositionComponent::class.java).y.toInt() / GRID_SIZE.toInt()

    protected val engine: Engine
        get() = ((Gdx.app.applicationListener as Flood).getScreen() as FloodScreen).engine

    fun move() {
        engine.getSystem(MovementSystem::class.java).SelectEntity(this)
    }
}
