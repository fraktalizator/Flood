package com.fraktalizator.flood.pathfinding.logic

import kotlin.math.abs


class Tile(var x: Int, var y: Int) {
    companion object {
        val X: Tile = Tile(1, 0)
        val Y: Tile = Tile(0, 1)
        val Zero: Tile = Tile(0, 0)
        const val SIZE = 32f
    }

    constructor() : this(0, 0)

    constructor(tile: Tile) : this(tile.x, tile.y)

    constructor(tile: ImmutableTile) : this(tile.x(), tile.y())


    fun len(): Int {
        return abs(x) + abs(y)
    }

    fun dst(tile: Tile): Int {
        val x_d = tile.x - this.x
        val y_d = tile.y - this.y
        return abs(x_d) + abs(y_d)
    }

    fun dst(x: Int, y: Int): Int {
        val x_d = x - this.x
        val y_d = y - this.y
        return abs(x_d) + abs(y_d)
    }

    operator fun plus(t: Tile): Tile {
        x += t.x
        y += t.y
        return this
    }

    operator fun minus(t: Tile): Tile {
        x -= t.x
        y -= t.y
        return this
    }

    operator fun times(t: Tile): Tile {
        x *= t.x
        y *= t.y
        return this
    }

    operator fun times(scalar: Int): Tile {
        x *= scalar
        y *= scalar
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Tile) return false
        if (other.x != this.x || other.y != this.y) return false
        return true
    }

    override fun hashCode(): Int {
        val prime = 31;//todo
        return x * y * prime
    }
}
