package com.fraktalizator.flood

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.componentes.*
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.componentes.PositionComponent.Companion.GRIDSIZE
import com.fraktalizator.flood.game_objects.RenderAbleEntity

class GameWorld : EntityListener {
    val tiledMap: TiledMap = Assets.MapAssets.TutorialIsland.map
    var mapRenderer: OrthogonalTiledMapRenderer = OrthogonalTiledMapRenderer(tiledMap, 1f)

    var entities: HashMap<Vector2, RenderAbleEntity> = HashMap()

    //loaded in loadScreen and is passed to the gameScreen
    val engine: Engine = Engine()

    init {
        engine.addEntityListener(this)
    }

    fun initNPC(): Boolean {
        val character = RenderAbleEntity(
            Vector2(16 * GRIDSIZE, 7 * GRIDSIZE),
            Assets.TextureAssets.PlayerMovementAnimation.texture, true
        )
        character.add(MoveComponent(12, MoveType.Normal))
        character.add(NameComponent("tomek"))
        character.add(TouchHandlingComponent({ ent -> ent.move() }, {}))// odpalic metode move

        engine.addEntity(character)
        return true
    }


    fun getTileCost(position: Vector2): Int {
        val cell =
            (tiledMap.layers[0] as TiledMapTileLayer).getCell(
                (position.x.toInt() / GRIDSIZE).toInt(),
                (position.y.toInt() / GRIDSIZE).toInt()
            )
                ?: return 9999
        return cell.tile.properties["moveCost"].toString().toInt()
    }


    fun dispose() {
        tiledMap.dispose()
        mapRenderer.dispose()
    }

    override fun entityAdded(entity: Entity) {
        if (entity is RenderAbleEntity) {
            val entPos: Vector2 = entity.getComponent(PositionComponent::class.java).position
            entities[entPos.scl(1/ GRIDSIZE)] = entity
        }
        println(entities)
    }

    override fun entityRemoved(entity: Entity) {
        if (entity is RenderAbleEntity) {
            val entPos: Vector2 = entity.getComponent(PositionComponent::class.java).position
            entities.remove(entPos.scl(1/ GRIDSIZE))
        }
    }
}
