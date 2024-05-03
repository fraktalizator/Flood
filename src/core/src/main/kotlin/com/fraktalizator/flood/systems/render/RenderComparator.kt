package com.fraktalizator.flood.systems.render

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.fraktalizator.flood.componentes.RenderComponent

class RenderComparator : Comparator<Entity?> {
    private val cmTrans: ComponentMapper<RenderComponent> = ComponentMapper.getFor(RenderComponent::class.java)

    override fun compare(entityA: Entity?, entityB: Entity?): Int {
        val A_EntZ = cmTrans[entityA].zindex
        val B_EntZ = cmTrans[entityB].zindex
        var res = 0
        if (A_EntZ > B_EntZ) {
            res = 1
        } else if (A_EntZ < B_EntZ) {
            res = -1
        }
        return res
    }
}
