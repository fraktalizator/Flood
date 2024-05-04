package com.fraktalizator.flood.entityEngine

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.entityEngine.componentes.*
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent.Companion.GRIDSIZE
import com.fraktalizator.flood.entityEngine.entities.RenderAbleEntity
import com.fraktalizator.flood.entityEngine.systems.input.InputSystem
import com.fraktalizator.flood.entityEngine.systems.MovementSystem
import com.fraktalizator.flood.entityEngine.systems.TiledMapRenderSystem
import com.fraktalizator.flood.entityEngine.systems.render.EntityRenderSystem
import com.fraktalizator.flood.extension_methods.TiledMapExtensions.getMoveCost
import com.fraktalizator.flood.pathfinding_alghoritms.Pathfinding
import com.fraktalizator.flood.screens.BaseScreen.Companion.bach

class WorldEngineInitializer(private val tiledMap: TiledMap){

    //loaded in loadScreen and is passed to the gameScreen
    val engine: Engine = Engine()

    init {
        val tiledMapRenderSystem = TiledMapRenderSystem(tiledMap)
        engine.addSystem(tiledMapRenderSystem)
        engine.addSystem(EntityRenderSystem(bach))
        engine.addSystem(MovementSystem(Pathfinding { tiledMap.getMoveCost(it) }))
        engine.addSystem(InputSystem(tiledMapRenderSystem.camera))
    }

    fun initNPC(): Boolean {
        val character = RenderAbleEntity(
            Vector2(2 * GRIDSIZE, 3 * GRIDSIZE),
            Assets.TextureAssets.PlayerMovementAnimation.texture, true
        )
        character.add(MoveComponent(12, MoveType.Normal))
        character.add(NameComponent("tomek"))
        character.add(TouchHandlingComponent({ ent -> ent.move() }, {}))// odpalic metode move

        engine.addEntity(character)
        return true
    }
}
