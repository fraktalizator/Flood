package com.fraktalizator.flood.componentes

import com.badlogic.ashley.core.Component

data class MoveComponent(
    var range: Int,
    var type: MoveType
) : Component

enum class MoveType(val moveTypeId: Int) {
    Normal(1),
    Flying(2),
    WaterWalker(3)
}
