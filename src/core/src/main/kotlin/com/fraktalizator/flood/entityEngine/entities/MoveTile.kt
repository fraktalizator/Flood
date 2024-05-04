package com.fraktalizator.flood.entityEngine.entities

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.entityEngine.componentes.PathToTileComponent
import com.fraktalizator.flood.entityEngine.componentes.PositionComponent
import com.fraktalizator.flood.entityEngine.componentes.RenderComponent
import com.fraktalizator.flood.entityEngine.componentes.TouchHandlingComponent
import com.fraktalizator.flood.pathfinding_alghoritms.Direction

class MoveTile(position: Vector2, pathTo: ArrayList<Direction>) :
    RenderAbleEntity(position, Assets.TextureAssets.MoveTile.texture, false) {
    private val moveTileTexture: TextureRegion = TextureRegion(Assets.TextureAssets.MoveTile.texture)
    private val moveTileSelectedTexture: TextureRegion = TextureRegion(Assets.TextureAssets.MoveTileSelected.texture)

    init {
        println("Move tile init")
        val pathToTileComponent: PathToTileComponent = PathToTileComponent(pathTo)
        getComponent(PositionComponent::class.java).height = 32f
        //val hoverHandlingComponent: HoverHandlingComponent = HoverHandlingComponent({select()}, {unSelect()})
        val touchHandlingComponent: TouchHandlingComponent = TouchHandlingComponent({}, {})
        getComponent(RenderComponent::class.java).zindex = (0)
        this.add(pathToTileComponent)
        //this.add(hoverHandlingComponent)
        this.add(touchHandlingComponent)
        println("Move tile init done")
    }

    fun select() {
        println("Move tile sel")
        getComponent(RenderComponent::class.java).textureRegion = (moveTileSelectedTexture)
    }

    fun unSelect() {
        println("Move tile des")
        getComponent(RenderComponent::class.java).textureRegion = (moveTileTexture)
    }
}
