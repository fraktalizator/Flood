package com.fraktalizator.flood.componentes

import com.badlogic.ashley.core.Component
import com.fraktalizator.flood.game_objects.RenderAbleEntity
import java.util.function.Consumer

data class TouchHandlingComponent(
    var LeftClickAction: Consumer<RenderAbleEntity>,
    var RightClickAction: Consumer<RenderAbleEntity>
) : Component
