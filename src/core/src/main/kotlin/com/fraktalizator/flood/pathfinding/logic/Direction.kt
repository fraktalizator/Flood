package com.fraktalizator.flood.pathfinding.logic

enum class Direction(
    val tile: ImmutableTile
) {
    Up(ImmutableTile(0, 1)),
    Right(ImmutableTile(1, 0)),
    Down(ImmutableTile(0, -1)),
    Left(ImmutableTile(-1, 0));
}
