package com.fraktalizator.flood.pathfinding_alghoritms

import com.badlogic.gdx.math.Vector2

enum class Direction(direction: String, var posFrame: Int) {
    Up("u", 0),
    Right("r", 1),
    Down("d", 2),
    Left("l", 3);

    var targetTilePos: Vector2 = Vector2(0f, 0f)

    init {
        when (direction) {
            "u" -> targetTilePos = Vector2(0f, 1f)
            "l" -> targetTilePos = Vector2(-1f, 0f)
            "d" -> targetTilePos = Vector2(0f, -1f)
            "r" -> targetTilePos = Vector2(1f, 0f)
        }
    }
}
