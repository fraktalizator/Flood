package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.pathfinding.logic.ImmutableTile
import com.fraktalizator.flood.pathfinding.logic.Tile

class PositionComponent(x: Float, y: Float, width: Float, height: Float) :
    Component {
    var bounds: Rectangle = Rectangle(0f, 0f, 0f, 0f)
        private set

    var x: Float = x
        set(value) {
            field = value
            updateBounds()
        }

    var y: Float = y
        set(value) {
            field = value
            updateBounds()
        }

    var width: Float = width
        set(value) {
            field = value
            updateBounds()
        }

    var height: Float = height
        set(value) {
            field = value
            updateBounds()
        }

    var position: Vector2
        get() = Vector2(x, y)
        set(position) {
            this.x = position.x
            this.y = position.y
            bounds = Rectangle(x, y, width, height)
        }

    fun tile() = Tile((x / Tile.SIZE).toInt(), (y / Tile.SIZE).toInt())
    fun immutableTile() = ImmutableTile((x / Tile.SIZE).toInt(), (y / Tile.SIZE).toInt())

    init {
        updateBounds()
    }

    private fun updateBounds() {
        this.bounds = Rectangle(x, y, width, height)
    }
}
