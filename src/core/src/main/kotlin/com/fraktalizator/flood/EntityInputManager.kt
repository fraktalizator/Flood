//package com.fraktalizator.flood
//
//import com.badlogic.gdx.InputProcessor
//import com.badlogic.gdx.math.Vector2
//import com.badlogic.gdx.math.Vector3
//import com.badlogic.gdx.utils.viewport.Viewport
//import com.fraktalizator.flood.entityEngine.WorldEngineInitializer
//import com.fraktalizator.flood.entityEngine.componentes.HoverHandlingComponent
//import com.fraktalizator.flood.entityEngine.componentes.PositionComponent.Companion.GRIDSIZE
//import com.fraktalizator.flood.entityEngine.componentes.RenderComponent
//import com.fraktalizator.flood.entityEngine.componentes.TouchHandlingComponent
//import com.fraktalizator.flood.entityEngine.entities.RenderAbleEntity
//
//class EntityInputManager(
//    private val floodScreenViewport: Viewport,
//    private val worldEngineInitializer: WorldEngineInitializer
//) : InputProcessor {
//    /*
//    ------------------------------------ Input Processor Functions ------------------------------------
//     */
//
//    override fun keyDown(keycode: Int): Boolean {
//        return false
//    }
//
//    override fun keyUp(keycode: Int): Boolean {
//        return false
//    }
//
//    override fun keyTyped(character: Char): Boolean {
//        return false
//    }
//
//    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        return false
//    }
//
//    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        val clickedEnt: RenderAbleEntity =
//            getRenderAbleEntityByScreenClick(screenX.toFloat(), screenY.toFloat()) ?: return false;
//
//        if (!clickedEnt.getComponent(RenderComponent::class.java).isVisible) return false
//
//        val touchComp: TouchHandlingComponent =
//            clickedEnt.getComponent(TouchHandlingComponent::class.java) ?: return false
//
//        //println("hit: ${clickedEnt} with button ${button}")
//        if (button == 0) {
//            touchComp.LeftClickAction.accept(clickedEnt)
//            return true
//        } else if (button == 1) {
//            touchComp.RightClickAction.accept(clickedEnt)
//            return true
//        }
//        return false
//    }
//
//    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
//        TODO("Not yet implemented")
//    }
//
//    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
//        return false
//    }
//
//    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
//        val clickedEnt: RenderAbleEntity =
//            getRenderAbleEntityByScreenClick(screenX.toFloat(), screenY.toFloat()) ?: return false
//
//        if (!clickedEnt.getComponent(RenderComponent::class.java).isVisible) return false // do nothing if invisible
//
//        // Un hover every entity
//        unHoverEntities(clickedEnt)
//
//        // Run hover consumer on selected entity
//        val handled: Boolean = hoverEntity(clickedEnt)
//
//        return handled
//    }
//
//    override fun scrolled(amountX: Float, amountY: Float): Boolean {
//        return false
//    }
//
//    /*
//    ------------------------------------ Main Functions ------------------------------------
//     */
//    private fun unHoverEntities(exceptionEntity: RenderAbleEntity) {
//        for (entity in worldEngineInitializer.entities.values) {
//            val hovComp = entity.getComponent(HoverHandlingComponent::class.java) ?: continue
//
//            if (hovComp.hovered && entity != exceptionEntity) {
//                hovComp.onHoverOffAction.accept(exceptionEntity)
//                hovComp.hovered = false
//            }
//        }
//    }
//
//    private fun hoverEntity(clickedEnt: RenderAbleEntity): Boolean {
//        val hovCompForSelectedEntity = clickedEnt.getComponent(HoverHandlingComponent::class.java)
//            ?: return false // do nothing if hover handling undefined
//        if (!hovCompForSelectedEntity.hovered) {
//            hovCompForSelectedEntity.onHoverAction.accept(clickedEnt)
//            hovCompForSelectedEntity.hovered = true
//            return true
//        }
//        return false
//    }
//    /*
//    ------------------------------------ Utils Functions ------------------------------------
//     */
//
//    private fun getRenderAbleEntityByScreenClick(x: Float, y: Float): RenderAbleEntity? =
//        getRenderAbleEntityByScreenClick(Vector2(x, y))
//
//    private fun getRenderAbleEntityByScreenClick(clickPos: Vector2): RenderAbleEntity? {
//        val clickPosition3 = floodScreenViewport.unproject(Vector3(clickPos.x, clickPos.y, 0f))
//        val clickedTile = Vector2(
//            clickPosition3.x - clickPosition3.x % 32,
//            clickPosition3.y - clickPosition3.y % 32
//        ).scl(1 / GRIDSIZE)
//
//        //println("tile = ${clickedTile}")
//        return worldEngineInitializer.entities[clickedTile]
//    }
//}
