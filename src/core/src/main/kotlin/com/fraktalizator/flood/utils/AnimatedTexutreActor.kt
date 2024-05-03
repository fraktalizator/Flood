package com.fraktalizator.flood.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor

class AnimatedTextureActor : Actor {
    private val playerAnimation: ArrayList<Animation<TextureRegion>>
    private var posFrame = 0
    private var elapsedTime = 0f
    var isFrozen: Boolean = false
        private set
    private var autoIncrementPosFrame = false

    private var textureForDispose: Texture? = null

    // this method can by used only if assets wosnt loaded yet !!!!!!!!!
    constructor(texture: Texture) {
        setSize(texture.width / 4f, texture.height / 4f)
        playerAnimation = AnimationGenerator.setUpAnimation(texture)
        textureForDispose = texture
    }

//    constructor(playerAnimation: ArrayList<Animation<TextureRegion>?>) {
//        setSize(
//            playerAnimation[0]!!.getKeyFrame(0f).regionWidth.toFloat(),
//            playerAnimation[0]!!.getKeyFrame(0f).regionHeight.toFloat()
//        )
//        this.playerAnimation = playerAnimation
//    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (!isFrozen) elapsedTime += Gdx.graphics.deltaTime
        if (playerAnimation.size > 0 && playerAnimation[posFrame] != null) batch.draw(
            playerAnimation[posFrame]!!.getKeyFrame(elapsedTime, true), x, y
        )
        if (autoIncrementPosFrame && playerAnimation.size > 0 && playerAnimation[4] != null) posFrame =
            ((playerAnimation[4]!!
                .animationDuration + elapsedTime) % 4).toInt()
    }

    fun dispose() {
        if (textureForDispose != null) textureForDispose!!.dispose()
    }

    override fun setSize(width: Float, height: Float) {
        super.setSize(width, height)
        setBounds(x, y, width, getHeight())
    }

    override fun setPosition(x: Float, y: Float) {
        super.setPosition(x, y)
        setBounds(getX(), y, width, height)
    }

    fun setPosFrame(posFrame: Int) {
        this.posFrame = posFrame % 4
    }

    fun resetElapsedTime() {
        elapsedTime = 0f
    }

    fun freeze(freeze: Boolean) {
        this.isFrozen = freeze
    }

    fun setAutoIncrementPosFrame(bool: Boolean) {
        autoIncrementPosFrame = bool
    }
}
