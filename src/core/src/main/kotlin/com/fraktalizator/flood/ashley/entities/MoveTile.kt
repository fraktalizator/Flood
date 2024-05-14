package com.fraktalizator.flood.ashley.entities

import com.badlogic.ashley.core.Entity
import com.fraktalizator.flood.ashley.componentes.HoverHandlingComponent
import com.fraktalizator.flood.ashley.componentes.PathToTileComponent
import com.fraktalizator.flood.ashley.componentes.PositionComponent
import com.fraktalizator.flood.ashley.componentes.RenderComponent
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.extension_methods.get
import com.fraktalizator.flood.extension_methods.remove
import com.fraktalizator.flood.pathfinding.logic.DirectionPath
import com.fraktalizator.flood.pathfinding.logic.ImmutableTile
import com.fraktalizator.flood.pathfinding.logic.Tile

class MoveTile : Entity() {
    var isInitialized = false
        private set

    companion object {
        private val moveTileRC: RenderComponent = RenderComponent(Assets.TextureAssets.MoveTile.texture, 0)
        private val moveTileSelectedRC: RenderComponent =
            RenderComponent(Assets.TextureAssets.MoveTileSelected.texture, 0)
    }

    fun init(immutableTile: ImmutableTile, pathTo: DirectionPath) {
        this.add(PathToTileComponent(pathTo))
        this.add(PositionComponent(immutableTile.x() * Tile.SIZE, immutableTile.y() * Tile.SIZE, Tile.SIZE, Tile.SIZE))
        this.add(HoverHandlingComponent({}, {}))
        this.add(moveTileRC)
        isInitialized = true
    }

    fun select() {
        if (!isInitialized) throw IllegalStateException("show method must be invoked to initialize MoveTile first")
        add(moveTileSelectedRC)
    }

    fun unSelect() {
        if (!isInitialized) throw IllegalStateException("show method must be invoked to initialize MoveTile first")
        add(moveTileRC)
    }

    fun reset() {
        remove<PathToTileComponent>()
        remove<PositionComponent>()
        remove<HoverHandlingComponent>()
        isInitialized = false
    }

    override fun toString(): String {
        return "MoveTile init: $isInitialized; pos: ${get<PositionComponent>()?.immutableTile()}"
    }
}
