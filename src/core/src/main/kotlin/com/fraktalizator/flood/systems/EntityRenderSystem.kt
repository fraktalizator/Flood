package com.fraktalizator.flood.systems

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.fraktalizator.flood.componentes.PositionComponent
import com.fraktalizator.flood.componentes.RenderComponent

class EntityRenderSystem(private val batch: SpriteBatch, private val camera: Camera) :
    SortedIteratingSystem(
        Family.all(PositionComponent::class.java, RenderComponent::class.java).get(),
        RenderComparator()
    ) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val renderComponent: RenderComponent = entity.getComponent(RenderComponent::class.java)
        if (!renderComponent.isVisible) return

        val positionComponent: PositionComponent = entity.getComponent(PositionComponent::class.java)

        if (!renderComponent.freezeAnimation) renderComponent.elapsedTime = (renderComponent.elapsedTime + deltaTime)

        batch.projectionMatrix = camera.combined
        batch.begin()
        if (renderComponent.isAnimate) {
            batch.draw(
                renderComponent.playerAnimation.get(renderComponent.posFrame)
                    .getKeyFrame(renderComponent.elapsedTime, true),
                positionComponent.x,
                positionComponent.y
            )
        } else {
            batch.draw(
                renderComponent.textureRegion,
                positionComponent.x,
                positionComponent.y,
                positionComponent.width,
                positionComponent.height
            )
        }
        batch.end()
//        if (entity.getComponent(DisplayTextComponent::class.java) != null) {
//            val text: Label = entity.getComponent(DisplayTextComponent::class.java).getLabel()
//            text.setPosition(positionComponent.getX() + 5, positionComponent.getY() + 26)
//            text.draw(batch, 1f)
//        }
    }
}
