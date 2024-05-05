package com.fraktalizator.flood.entityEngine.systems.input

import com.badlogic.gdx.graphics.OrthographicCamera
import com.fraktalizator.flood.entityEngine.componentes.HoverHandlingComponent
import com.fraktalizator.flood.entityEngine.componentes.TouchHandlingComponent
import com.fraktalizator.flood.entityEngine.entities.RenderAbleEntity

class InputSystem(camera: OrthographicCamera) : BaseInputSystem(camera) {

    override fun clickDown(entity: RenderAbleEntity) {
        entity.getComponent(TouchHandlingComponent::class.java).leftClickAction.accept(entity)
    }

    override fun clickUp() {
        //TODO("Not yet implemented")
    }

    override fun hoverOn(entity: RenderAbleEntity) {
        entity.getComponent(HoverHandlingComponent::class.java).onHoverAction.accept(entity)
    }

    override fun hoverOff(entity: RenderAbleEntity) {
        entity.getComponent(HoverHandlingComponent::class.java).onHoverOffAction.accept(entity)
    }

}
