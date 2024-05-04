package com.fraktalizator.flood.entityEngine.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fraktalizator.flood.utils.AnimationGenerator

class RenderComponent : Component {
    //todo refactor
    //for non animated entities
    var texture: Texture? = null

    //for all entities
    var zindex: Int
    var isVisible: Boolean = true

    //for  animated entities
    val isAnimate: Boolean
    var textureRegion: TextureRegion
    var elapsedTime: Float = 0f
    var posFrame: Int = 0
        set(posFrame) {
            field = posFrame % 4
        }
    var freezeAnimation: Boolean = false
    var playerAnimation: ArrayList<Animation<TextureRegion>> = ArrayList(5)
        private set
    private var frameWidth = 0f
    private var frameHeight = 0f

    constructor(texture: Texture?, Zindex: Int) {
        this.zindex = Zindex
        this.isAnimate = false
        textureRegion = TextureRegion(texture)
        textureRegion.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
    }

    constructor(texture: Texture?, Zindex: Int, frameWidth: Float, frameHeight: Float) {
        this.zindex = Zindex
        this.isAnimate = true
        textureRegion = TextureRegion(texture)
        this.frameWidth = frameWidth
        this.frameHeight = frameHeight
        require(!(frameWidth * 4 != textureRegion.regionWidth.toFloat() || frameHeight * 4 != textureRegion.regionHeight.toFloat())) { "Unable to create animation from texture this size" }
        textureRegion.texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
        playerAnimation = AnimationGenerator.setUpAnimation(texture)
    }
}
