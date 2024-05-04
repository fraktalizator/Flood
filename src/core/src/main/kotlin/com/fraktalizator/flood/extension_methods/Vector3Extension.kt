package com.fraktalizator.flood.extension_methods

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

object Vector3Extension {
    fun Vector3.toVector2(): Vector2 {
        return Vector2(this.x, this.y);
    }
}
