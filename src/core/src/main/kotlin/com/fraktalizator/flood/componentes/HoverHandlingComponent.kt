package com.fraktalizator.flood.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.game_objects.RenderAbleEntity
import java.util.function.Consumer

data class HoverHandlingComponent (
    var onHoverAction: Consumer<RenderAbleEntity>,
    var onHoverOffAction: Consumer<RenderAbleEntity>
) : Component {
    var hovered = false
}
