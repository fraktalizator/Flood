package com.fraktalizator.flood.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.GameWorld
import java.util.function.Consumer

import com.fraktalizator.flood.game_objects.MoveTile
import com.fraktalizator.flood.game_objects.RenderAbleEntity
import com.fraktalizator.flood.componentes.*
import com.fraktalizator.flood.componentes.PositionComponent.Companion.GRIDSIZE
import com.fraktalizator.flood.systems.render.EntityRenderSystem
import com.fraktalizator.flood.utils.Pathfinding

class MovementSystem(val gameWorld: GameWorld) : EntitySystem() {
    // select and path display system
    private var currentSelectedEntity: RenderAbleEntity? = null
    private val moveTilesAndPositions: HashMap<Vector2, MoveTile> = HashMap<Vector2, MoveTile>()
    private val currentSelectedMoveTiles: ArrayList<MoveTile?> = ArrayList<MoveTile?>()
    private val pathfinding: Pathfinding

    //move system
    private var isEntityMoving = false
    private var currentMovingEntityPath: ArrayList<Pathfinding.Direction> = ArrayList<Pathfinding.Direction>()
    private var movedPixels = 0
    private val MOVMENT_SPEED = 1 // ONLY POWERS OF 2 !!!!!!!!

    init {
        this.pathfinding = Pathfinding(gameWorld)
    }

    fun SelectEntity(entity: RenderAbleEntity) {
        if (isEntityMoving) return
        if (!entity.equals(currentSelectedEntity) && currentSelectedEntity != null) {
            disposeOldMoveTiles()
        } else if (entity.equals(currentSelectedEntity)) {
            return
        }
        currentSelectedEntity = entity


        val range: Int = entity.getComponent(MoveComponent::class.java).range
        val position: Vector2 = entity.getComponent(PositionComponent::class.java).position
        //MoveComponent.MoveType moveType = entity.getComponent(MoveComponent.class).getType();
        //TODO moveTYPE
        val positionAndMoveCosts: HashMap<Vector2, Int> = pathfinding.flood(position, range)

        val tilesInRangePositions: ArrayList<Vector2> = pathfinding.getTilesInRange(positionAndMoveCosts, range)

        for (tilePosition in tilesInRangePositions) {
            if (tilePosition.x == position.x && tilePosition.y == position.y) continue
            generateMoveTile(tilePosition, positionAndMoveCosts)
        }


        //test
//        for (Vector2 tilePosition : positionAndMoveCosts.keySet()) {
//            if(tilePosition.x == position.x && tilePosition.y == position.y) continue;
//            displayTilesAndCost(tilePosition, positionAndMoveCosts);
//        }
    }

    private fun generateMoveTile(tilePosition: Vector2, positionAndMoveCosts: HashMap<Vector2, Int>) {
        val path: ArrayList<Pathfinding.Direction> = pathfinding.getPath(
            currentSelectedEntity!!.getComponent(
                PositionComponent::class.java
            ).position, tilePosition, positionAndMoveCosts
        )
        val moveTile: MoveTile = MoveTile(tilePosition, path)
        val displayPathToAction: Consumer<RenderAbleEntity> =
            Consumer<RenderAbleEntity> { ent ->
                pathDisplay(path)
            }
        moveTile.add(HoverHandlingComponent(displayPathToAction) {})
        moveTile.getComponent(TouchHandlingComponent::class.java).LeftClickAction = Consumer<RenderAbleEntity>{
            gameWorld.entities.remove(currentSelectedEntity!!.getComponent(PositionComponent::class.java).position)
            initMoveEntity(it as MoveTile)
            disposeOldMoveTiles()
        }
        moveTilesAndPositions[tilePosition] = moveTile
        //moveTile.add(new DisplayTextComponenet(tilesInRangePossitions.get(tile).toString()));
        super.getEngine().addEntity(moveTile)
    }

    override fun update(deltaTime: Float) {
        if (!isEntityMoving) return

        if (doneMovingOneTile(currentMovingEntityPath[0])) {
            currentMovingEntityPath.removeAt(0)

            if (currentMovingEntityPath.size == 0) {
                val entPos: Vector2 = currentSelectedEntity!!.getComponent(PositionComponent::class.java).position.scl(1/ GRIDSIZE)
                gameWorld.entities.put(entPos, currentSelectedEntity!!)

                // so that entity foot wont be rendered on top of other ent head
                currentSelectedEntity!!.setZIndexByPosition()
                engine.getSystem(EntityRenderSystem::class.java).forceSort()

                isEntityMoving = false
                currentSelectedEntity = null
            }
        }
    }

    //    @Override
    //    public void update(float deltaTime) {
    //        if (!isEntityMoving) return;
    //        if (doneMovingOneTile(currentMovingEntityPath.get(currentMovingDirectionIndex))) {
    //            if (currentMovingDirectionIndex >= currentMovingEntityPath.size() - 1) {
    //                Vector2 entPos = currentSelectedEntity.getComponent(PositionComponent.class).getPosition();
    //                GameWorld.entities.put(entPos, currentSelectedEntity);
    //                // so that entity foot wont be rendered on top of other ent head
    //
    //                currentSelectedEntity.setZIndexByPosition();
    //                getEngine().getSystem(RenderSystem.class).forceSort();
    //
    //                isEntityMoving = false;
    //                currentSelectedEntity = null;
    //                currentMovingDirectionIndex = 0;
    //            }
    //            currentMovingDirectionIndex++;
    //        }
    //    }
    private fun doneMovingOneTile(direction: Pathfinding.Direction): Boolean {
        val currentSelectedEntityPosition: Vector2 =
            currentSelectedEntity!!.getComponent(PositionComponent::class.java).position
        currentSelectedEntity!!.getComponent(PositionComponent::class.java)
            .position = (currentSelectedEntityPosition.add(Vector2(direction.targetTilePos).scl(MOVMENT_SPEED.toFloat())))
        currentSelectedEntity!!.getComponent(RenderComponent::class.java).posFrame = (direction.posFrame)
        movedPixels = movedPixels + MOVMENT_SPEED
        if (movedPixels >= 32) {
            movedPixels = 0
            return true
        }
        return false
    }


    fun pathDisplay(directions: ArrayList<Pathfinding.Direction>) {
        val pos = Vector2(currentSelectedEntity!!.getComponent(PositionComponent::class.java).position)
        for (i in currentSelectedMoveTiles.indices) {
            currentSelectedMoveTiles[i]!!.unSelect()
        }
        for (direction in directions) {
            pos.add(Vector2(direction.targetTilePos).scl(GRIDSIZE))
            moveTilesAndPositions[pos]!!.select()
            currentSelectedMoveTiles.add(moveTilesAndPositions[pos])
        }
    }

    private fun resetMoveTiles() {
        for (moveTile in moveTilesAndPositions.values) {
            moveTile.unSelect()
        }
    }


    private fun initMoveEntity(targetMoveTile: MoveTile) {
        isEntityMoving = true
        currentMovingEntityPath = targetMoveTile.getComponent(PathToTileComponent::class.java).pathToTile
    }

    fun disposeOldMoveTiles() {
        if (currentSelectedEntity == null) return
        engine.removeAllEntities(Family.all(PathToTileComponent::class.java).get())
    }

    fun getCurrentSelectedEntity(): Entity? {
        return currentSelectedEntity
    }

    //var moveAction: Consumer<RenderAbleEntity> = Consumer<RenderAbleEntity>.pass
//        Consumer<RenderAbleEntity> {
//            gameWorld.entities.remove(currentSelectedEntity!!.getComponent(PositionComponent::class.java).position)
//            initMoveEntity(it as MoveTile)
//            disposeOldMoveTiles()
//            true
//        }

    //-------------------------------------------------------------------------------------------------------
    //--------------------------------------- FOR PATHFINDING TESTING ---------------------------------------
    //-------------------------------------------------------------------------------------------------------
    //    private void displayTilesAndCost(Vector2 tilePosition, HashMap<Vector2, Integer> positionAndMoveCosts){
    //        MoveTile moveTile = new MoveTile(tilePosition, null);
    //
    //        moveTile.getComponent(TouchHandlingComponent.class).setRightClickAction(new Consumer<RenderAbleEntity>() {
    //            @Override
    //            public boolean act(Entity entity) {
    //                entity.getComponent(PositionComponent.class).setPosition(tilePosition);
    //                return true;
    //            }
    //        });
    //        moveTilesAndPositions.put(tilePosition, moveTile);
    //        moveTile.add(new DisplayTextComponenet(positionAndMoveCosts.get(tilePosition).toString()));
    //        super.getEngine().addEntity(moveTile);
    //    }
    //--------------------------------------- FOR PATHFINDING TESTING ---------------------------------------
    //    public void SelectEntity(RenderAbleEntity entity) {
    //        if(isEntityMoving) return;
    //        if (!entity.equals(currentSelectedEntity) && currentSelectedEntity != null) {
    //            disposeOldMoveTiles();
    //        } else if (entity.equals(currentSelectedEntity)) {
    //            return;
    //        }
    //        currentSelectedEntity = entity;
    //
    //
    //        int range = entity.getComponent(MoveComponent.class).getRange();
    //        Vector2 position = entity.getComponent(PositionComponent.class).getPosition();
    //        HashMap<Vector2, Integer> positionAndMoveCosts = pathfinding.flood(position, range);
    //
    //        ArrayList<Vector2> tilesInRangePositions = pathfinding.getTilesInRange(positionAndMoveCosts, range);
    //
    //        for (Vector2 tilePosition : positionAndMoveCosts.keySet()) {
    //            if(tilePosition.x == position.x && tilePosition.y == position.y) continue;
    //            displayTilesAndCost(tilePosition, positionAndMoveCosts);
    //        }
    //    }


}
