package com.fraktalizator.flood.entityEngine.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class PositionComponent(x: Float, y: Float, width: Float, height: Float) : Component {
    companion object {
        const val GRID_SIZE: Float = 32f
    }

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

    fun getTilePosition() = position.scl(1/ GRID_SIZE).also { x.toInt(); y.toInt() }

    init {
        updateBounds()
    }

    private fun updateBounds() {
        this.bounds = Rectangle(x, y, width, height)
    }
}
