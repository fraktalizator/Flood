package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import java.util.function.Consumer

data class TouchHandlingComponent(
    var leftClickAction: Consumer<Entity>,
    var rightClickAction: Consumer<Entity>
) : Component
