package com.fraktalizator.flood.entityEngine.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.entityEngine.entities.RenderAbleEntity
import java.util.function.Consumer

data class TouchHandlingComponent(
    var leftClickAction: Consumer<RenderAbleEntity>,
    var rightClickAction: Consumer<RenderAbleEntity>
) : Component
