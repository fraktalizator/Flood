package com.fraktalizator.flood.extension_methods

import com.badlogic.gdx.math.Vector2

operator fun Vector2.minus(v: Vector2): Vector2 = Vector2(this.x-v.x, this.y-v.y)

operator fun Vector2.plus(v: Vector2): Vector2 = Vector2(this.x+v.x, this.y+v.y)

operator fun Vector2.times(v: Vector2): Vector2 = Vector2(this.x*v.x, this.y*v.y)
operator fun Vector2.times(scalar: Float): Vector2 = Vector2(this.x * scalar, this.y * scalar)


