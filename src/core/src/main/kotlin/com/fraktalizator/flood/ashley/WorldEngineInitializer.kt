package com.fraktalizator.flood.ashley


import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.ashley.actions.pathfinding.EntityPathfindingAction
import com.fraktalizator.flood.ashley.actions.pathfinding.MoveToTileAction
import com.fraktalizator.flood.ashley.actions.pathfinding.PathDisplayAction
import com.fraktalizator.flood.ashley.componentes.MoveComponent
import com.fraktalizator.flood.ashley.componentes.NameComponent
import com.fraktalizator.flood.ashley.componentes.PathfindingComponent
import com.fraktalizator.flood.ashley.entities.RenderAbleEntity
import com.fraktalizator.flood.ashley.systems.TiledMapRenderSystem
import com.fraktalizator.flood.ashley.systems.input.InputSystem
import com.fraktalizator.flood.ashley.systems.movement.MovementSystem
import com.fraktalizator.flood.ashley.systems.render.EntityRenderSystem
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.extension_methods.addAction
import com.fraktalizator.flood.extension_methods.getMoveCost
import com.fraktalizator.flood.pathfinding.MoveType
import com.fraktalizator.flood.pathfinding.algorithms.FloodPathfinding
import com.fraktalizator.flood.pathfinding.logic.DirectionPath
import com.fraktalizator.flood.pathfinding.logic.Tile
import com.fraktalizator.flood.screens.BaseScreen.Companion.bach

class WorldEngineInitializer(private val tiledMap: TiledMap) {

    //loaded in loadScreen and is passed to the gameScreen
    val engine: Engine =
        Engine()

    init {
        val tiledMapRenderSystem = TiledMapRenderSystem(tiledMap)
        engine.addSystem(tiledMapRenderSystem)
        engine.addSystem(EntityRenderSystem(bach))
        engine.addSystem(MovementSystem())
        engine.addSystem(InputSystem(tiledMapRenderSystem.camera))

        engine.addAction(EntityPathfindingAction(FloodPathfinding { tiledMap.getMoveCost(it) }))
        engine.addAction(PathDisplayAction())
        engine.addAction(MoveToTileAction())

        initNPC()
    }

    private fun initNPC(): Boolean {
        val character = RenderAbleEntity(
            Vector2(2 * Tile.SIZE, 3 * Tile.SIZE),
            Assets.TextureAssets.PlayerMovementAnimation.texture, true
        )
        character.add(PathfindingComponent(12, MoveType.Normal))
        character.add(NameComponent("tomek"))
        character.add(MoveComponent(DirectionPath(emptyList())))

        engine.addEntity(character)


        val character2 = RenderAbleEntity(
            Vector2(2 * Tile.SIZE, 4 * Tile.SIZE),
            Assets.TextureAssets.PlayerMovementAnimation.texture, true
        )
        character2.add(PathfindingComponent(17, MoveType.Normal))
        character2.add(NameComponent("tomek2"))
        character2.add(MoveComponent(DirectionPath(emptyList())))

        engine.addEntity(character2)
        return true
    }
}
