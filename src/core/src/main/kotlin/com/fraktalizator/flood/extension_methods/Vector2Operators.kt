package com.fraktalizator.flood.extension_methods

import com.badlogic.gdx.math.Vector2

operator fun Vector2.minus(v: Vector2): Vector2 = this.sub(v)

operator fun Vector2.plus(v: Vector2): Vector2 = this.add(v)

fun Vector2.safeScl(scalar: Float): Vector2 {
    return Vector2(this.x * scalar, this.y * scalar)
}

