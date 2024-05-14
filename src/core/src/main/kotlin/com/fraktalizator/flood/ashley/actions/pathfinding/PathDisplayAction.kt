package com.fraktalizator.flood.ashley.actions.pathfinding

import com.badlogic.ashley.core.Entity
import com.fraktalizator.flood.EntityAction
import com.fraktalizator.flood.ashley.componentes.PathToTileComponent
import com.fraktalizator.flood.ashley.entities.MoveTile
import com.fraktalizator.flood.extension_methods.get

class PathDisplayAction : EntityAction() {

    override fun act(entity: Entity) {
        if (entity !is MoveTile) return

        unSelectAllMoveTiles()

        showPathToTile(entity)
    }

    private fun unSelectAllMoveTiles() {
        MoveTilePool.unselectTiles()
    }

    private fun showPathToTile(moveTile: MoveTile) {
        val pathToTileCom = moveTile.get<PathToTileComponent>()!!
        val moveTiles = MoveTilePool.getMoveTilesByPath(moveTile, pathToTileCom.pathToTile)
        println(moveTiles)
        moveTile.select()
        println("path " + moveTile.get<PathToTileComponent>()!!.pathToTile)
        for (moveTileInPath in moveTiles) {
            moveTileInPath.select()
        }
    }
}
