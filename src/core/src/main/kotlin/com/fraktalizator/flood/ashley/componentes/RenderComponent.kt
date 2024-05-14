package com.fraktalizator.flood.ashley.componentes

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.fraktalizator.flood.utils.AnimationGenerator

class RenderComponent : Component {
    //general
    var zindex: Int
    var isVisible: Boolean = true
    private var textureRegion: TextureRegion

    //for animations
    var freezeAnimation: Boolean = false
    private val isAnimate: Boolean
    var elapsedTime: Float = 0f
    var posFrame: Int = 0
        set(posFrame) {
            field = posFrame % 4
        }
    private var frameWidth = 0f
    private var frameHeight = 0f
    private lateinit var playerAnimation: ArrayList<Animation<TextureRegion>>

    fun getTexture(): TextureRegion {
        return if (isAnimate) {
            playerAnimation[posFrame].getKeyFrame(elapsedTime, true)
        } else {
            textureRegion
        }
    }

    constructor(texture: Texture?, zIndex: Int) {
        this.zindex = zIndex
        this.isAnimate = false
        textureRegion = TextureRegion(texture)
    }

    constructor(texture: Texture?, zIndex: Int, frameWidth: Float, frameHeight: Float) {
        this.zindex = zIndex
        this.isAnimate = true
        textureRegion = TextureRegion(texture)
        this.frameWidth = frameWidth
        this.frameHeight = frameHeight
        //todo make AnimationGenerator generate animations with different than 4x4 dimensions
        require(!(frameWidth * 4 != textureRegion.regionWidth.toFloat() || frameHeight * 4 != textureRegion.regionHeight.toFloat())) { "Unable to create animation from texture this size" }
        playerAnimation = AnimationGenerator.setUpAnimation(texture)
    }
}
