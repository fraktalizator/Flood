package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import java.util.function.Consumer

data class HoverHandlingComponent(
    var onHoverAction: Consumer<Entity>,
    var onHoverOffAction: Consumer<Entity>
) : Component {
    var hovered = false
}
