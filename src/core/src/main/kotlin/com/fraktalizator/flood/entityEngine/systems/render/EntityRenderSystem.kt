package com.fraktalizator.flood.entityEngine.systems.render

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent
import com.fraktalizator.flood.entityEngine.componentes.RenderComponent

class EntityRenderSystem(
    private val batch: SpriteBatch
) : SortedIteratingSystem(
    Family.all(PositionComponent::class.java, RenderComponent::class.java).get(),
    RenderComparator()
) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val renderComponent: RenderComponent = entity.getComponent(RenderComponent::class.java)
        if (!renderComponent.isVisible) return

        val positionComponent: PositionComponent = entity.getComponent(PositionComponent::class.java)

        if (!renderComponent.freezeAnimation) renderComponent.elapsedTime = (renderComponent.elapsedTime + deltaTime)

        batch.begin()

        batch.draw(
            renderComponent.getTexture(),
            positionComponent.x,
            positionComponent.y,
        )

        batch.end()
//        if (entity.getComponent(DisplayTextComponent::class.java) != null) {
//            val text: Label = entity.getComponent(DisplayTextComponent::class.java).getLabel()
//            text.setPosition(positionComponent.getX() + 5, positionComponent.getY() + 26)
//            text.draw(batch, 1f)
//        }
    }
}
