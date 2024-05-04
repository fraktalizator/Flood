package com.fraktalizator.flood.game_objects

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.fraktalizator.flood.componentes.*
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.utils.Pathfinding

class MoveTile(position: Vector2, pathTo: ArrayList<Pathfinding.Direction>) :
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
