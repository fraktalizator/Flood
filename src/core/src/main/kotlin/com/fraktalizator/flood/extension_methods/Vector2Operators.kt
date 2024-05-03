package com.fraktalizator.flood.extension_methods

import com.badlogic.gdx.math.Vector2

object Vector2Operators {

    operator fun Vector2.minus(v:Vector2): Vector2 {
        return Vector2(this.x-v.x, this.y-v.y)
    }
}
