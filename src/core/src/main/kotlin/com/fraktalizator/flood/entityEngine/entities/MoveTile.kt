package com.fraktalizator.flood.entityEngine.entities

import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.entityEngine.componentes.PathToTileComponent
import com.fraktalizator.flood.entityEngine.componentes.RenderComponent
import com.fraktalizator.flood.pathfinding_alghoritms.Direction

class MoveTile(position: Vector2, pathTo: ArrayList<Direction>) :
    RenderAbleEntity(position, Assets.TextureAssets.MoveTile.texture, false) {
    private val moveTileRC: RenderComponent = RenderComponent(Assets.TextureAssets.MoveTile.texture, 0)
    private val moveTileSelectedRC: RenderComponent = RenderComponent(Assets.TextureAssets.MoveTileSelected.texture, 0)

    init {
        unSelect()
        add(PathToTileComponent(pathTo))
    }

    fun select() {
        add(moveTileSelectedRC)
    }

    fun unSelect() {
        add(moveTileRC)
    }
}
