package com.fraktalizator.flood.pathfinding.logic

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.GdxRuntimeException
import com.fraktalizator.flood.extension_methods.plus


class DirectionPath(
    private val directionList: List<Direction>
) {
    var pathIndex: Int = -1

    fun getDirection(): Direction {
        if (!isStarted()) start()
        return directionList[pathIndex]
    }

    fun length() = directionList.size

    fun isStarted() = pathIndex != -1

    fun start() = run { this.pathIndex = 0 }

    fun reset() = run { this.pathIndex = -1 }

    fun isEnded() = pathIndex == directionList.size

    fun directionReached() {
        if (isEnded()) throw GdxRuntimeException("Path is already ended")
        pathIndex++
    }

    fun getWalkedVector(): Vector2 {
        if (pathIndex == 0 || pathIndex == -1) return Vector2.Zero
        var walked = Vector2()
        directionList.forEachIndexed { index, direction ->
            if (index < pathIndex) {
                walked += direction.tile.toVector().scl(Tile.SIZE)
            }
        }
        return walked
    }


    override fun toString(): String {
        return "$directionList index: $pathIndex"
    }

    operator fun get(index: Int): Direction {
        return directionList[index]
    }

    operator fun iterator(): Iterator<Direction> {
        return directionList.listIterator()
    }

    companion object {
        fun reverse(directionPathList: List<Direction>): DirectionPath {
            val reversedDirectionList = ArrayList<Direction>(directionPathList.size)
            for (direction in directionPathList.reversed()) {
                when (direction) {
                    Direction.Up -> reversedDirectionList.add(Direction.Down)
                    Direction.Right -> reversedDirectionList.add(Direction.Left)
                    Direction.Down -> reversedDirectionList.add(Direction.Up)
                    Direction.Left -> reversedDirectionList.add(Direction.Right)
                }
            }
            return DirectionPath(reversedDirectionList)
        }

        fun reverse(directionPath: DirectionPath): DirectionPath {
            return reverse(directionPath.directionList)
        }
    }
}
