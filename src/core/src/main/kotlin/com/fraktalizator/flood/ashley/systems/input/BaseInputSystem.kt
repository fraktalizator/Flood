package com.fraktalizator.flood.ashley.systems.input

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.fraktalizator.flood.ashley.componentes.HoverHandlingComponent
import com.fraktalizator.flood.ashley.componentes.PositionComponent
import com.fraktalizator.flood.ashley.componentes.RenderComponent
import com.fraktalizator.flood.extension_methods.toVector2

abstract class BaseInputSystem(private val camera: OrthographicCamera) : IteratingSystem(
    Family.all(PositionComponent::class.java, RenderComponent::class.java).get()
) {
    private var isCurrentlyClicked = false

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        manageMouseClick(entity)
        manageMouseHover(entity)
    }

    /**
     * mouse left click on RenderAbleEntity
     */
    private fun manageMouseClick(entity: Entity?) {
        if (entity == null) return
        //if (entity !is RenderAbleEntity) return

        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (isCurrentlyClicked) {
                clickUp()
                isCurrentlyClicked = false
            }
            return
        }
        if (isCurrentlyClicked) return

        val positionComponent = entity.getComponent(PositionComponent::class.java)
        if (!positionComponent.bounds.contains(getMouseClickPos())) return

        if (!isCurrentlyClicked) {
            clickDown(entity)
            isCurrentlyClicked = true
        }
    }

    abstract fun clickDown(entity: Entity)
    abstract fun clickUp()

    /**
     * mouse Hover on RenderAbleEntity
     */
    private fun manageMouseHover(entity: Entity?) {
        if (entity == null) return
        //if (entity !is RenderAbleEntity) return

        val hoverHandlingComponent = entity.getComponent(HoverHandlingComponent::class.java) ?: return
        val positionComponent = entity.getComponent(PositionComponent::class.java) ?: return

        if (positionComponent.bounds.contains(getMouseClickPos())) {
            if (!hoverHandlingComponent.hovered) {
                hoverOn(entity)
                hoverHandlingComponent.hovered = true
            }
        } else {
            if (hoverHandlingComponent.hovered) {
                hoverOff(entity)
                hoverHandlingComponent.hovered = false
            }
        }
    }

    abstract fun hoverOn(entity: Entity)
    abstract fun hoverOff(entity: Entity)


    protected fun getMouseClickPos(): Vector2 {
        return camera.unproject(Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)).toVector2()
    }
}
