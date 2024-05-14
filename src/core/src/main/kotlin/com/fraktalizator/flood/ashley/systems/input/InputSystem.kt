package com.fraktalizator.flood.ashley.systems.input

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import com.fraktalizator.flood.ashley.actions.pathfinding.EntityPathfindingAction
import com.fraktalizator.flood.ashley.actions.pathfinding.MoveToTileAction
import com.fraktalizator.flood.ashley.actions.pathfinding.PathDisplayAction
import com.fraktalizator.flood.ashley.componentes.HoverHandlingComponent
import com.fraktalizator.flood.ashley.componentes.PathfindingComponent
import com.fraktalizator.flood.ashley.componentes.TouchHandlingComponent
import com.fraktalizator.flood.ashley.entities.MoveTile
import com.fraktalizator.flood.extension_methods.get
import com.fraktalizator.flood.extension_methods.getAction

class InputSystem(camera: OrthographicCamera) : BaseInputSystem(camera) {

    override fun clickDown(entity: Entity) {
        entity.get<TouchHandlingComponent>()?.leftClickAction?.accept(entity)

        if (entity.get<PathfindingComponent>() != null) {
            engine.getAction<EntityPathfindingAction>()!!.act(entity)
        }

        if (entity is MoveTile) {
            engine.getAction<MoveToTileAction>()?.act(entity)
        }

    }

    override fun clickUp() {
        //TODO("Not yet implemented")
    }

    override fun hoverOn(entity: Entity) {
        entity.get<HoverHandlingComponent>()?.onHoverAction?.accept(entity)
        if (entity is MoveTile) {
            engine.getAction<PathDisplayAction>()?.act(entity)
        }
    }

    override fun hoverOff(entity: Entity) {
        entity.get<HoverHandlingComponent>()?.onHoverOffAction?.accept(entity)
    }

}
