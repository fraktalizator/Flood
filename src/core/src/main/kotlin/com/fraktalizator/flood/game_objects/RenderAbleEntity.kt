package com.fraktalizator.flood.game_objects

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.componentes.PositionComponent
import com.fraktalizator.flood.componentes.PositionComponent.Companion.GRIDSIZE
import com.fraktalizator.flood.componentes.RenderComponent
import com.fraktalizator.flood.screens.flood.FloodScreen
import com.fraktalizator.flood.systems.MovementSystem

open class RenderAbleEntity(position: Vector2, texture: Texture?, animate: Boolean) : Entity() {
    var moveCost: Int = 9999

    init {
        if (animate) {
            add(PositionComponent(position.x, position.y, 32f, 48f))
            add(RenderComponent(texture, zIndexByPosition, 32f, 48f))
        } else {
            add(PositionComponent(position.x, position.y, 32f, 48f))
            add(RenderComponent(texture, zIndexByPosition))
        }
    }

    fun setZIndexByPosition() {
        getComponent(RenderComponent::class.java).zindex = (zIndexByPosition)
    }

    private val zIndexByPosition: Int
        get() = getComponent(PositionComponent::class.java).y.toInt() / GRIDSIZE.toInt()

    protected val engine: Engine
        get() = ((Gdx.app.applicationListener as Flood).getScreen() as FloodScreen).engine

    fun move(){
        engine.getSystem(MovementSystem::class.java).SelectEntity(this)
    }
}
