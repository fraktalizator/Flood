package com.fraktalizator.flood.pathfinding.logic

import com.badlogic.gdx.math.Vector2

class ImmutableTile(
    private val internalTile: Tile
) {

    constructor() : this(Tile(0, 0))

    constructor(x: Int, y: Int) : this(Tile(x, y))
    constructor(immutableTile: ImmutableTile) : this(Tile(immutableTile.x(), immutableTile.y()))

    fun x() = internalTile.x
    fun y() = internalTile.y

    fun dst(tile: Tile): Int {
        return this.internalTile.dst(tile)
    }

    fun dst(x: Int, y: Int): Int {
        return this.internalTile.dst(x, y)
    }

    fun dst(immutableTile: ImmutableTile): Int {
        return dst(immutableTile.x(), immutableTile.y())
    }

    operator fun plus(t: ImmutableTile): ImmutableTile {
        return ImmutableTile(x() + t.x(), y() + t.y())
    }

    operator fun minus(t: ImmutableTile): ImmutableTile {
        return ImmutableTile(x() - t.x(), y() - t.y())
    }

    operator fun times(t: ImmutableTile): ImmutableTile {
        return ImmutableTile(x() * t.x(), y() * t.y())
    }

    operator fun times(scalar: Int): ImmutableTile {
        return ImmutableTile(x() * scalar, y() * scalar)
    }

    fun toVector() = Vector2(x().toFloat(), y().toFloat())

    override fun equals(other: Any?): Boolean {
        if (other !is ImmutableTile) return false
        if (other.x() != x() || other.y() != y()) return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31
        return prime * x() * y()
    }

    override fun toString(): String {
        return "ImmutableTile x:${x()}, y: ${y()}"
    }
}
