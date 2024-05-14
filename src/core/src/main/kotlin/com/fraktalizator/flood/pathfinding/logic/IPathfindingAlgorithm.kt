package com.fraktalizator.flood.pathfinding.logic

interface IPathfindingAlgorithm {
    /**
     * Returns HashMap with all tile positions (key) and their corresponding cost (value) within the distance of range.
     */
    fun flood(
        startTile: ImmutableTile,
        range: Int
    ): Map<ImmutableTile, Int>

    /**
     * Returns cheapest DirectionPath from startTile toEndTile based on preComputed positionAndMoveCosts
     */
    fun getPath(
        startTile: ImmutableTile,
        endTile: ImmutableTile,
        positionAndMoveCosts: Map<ImmutableTile, Int>
    ): DirectionPath

    fun getTilesInRange(
        startTile: ImmutableTile,
        range: Int
    ): List<ImmutableTile>

    /**
     * Returns all accessible tiles based on the range
     */
    fun getTilesInRange(
        startTile: ImmutableTile,
        range: Int,
        positionAndMoveCosts: Map<ImmutableTile, Int>
    ): List<ImmutableTile>
}
