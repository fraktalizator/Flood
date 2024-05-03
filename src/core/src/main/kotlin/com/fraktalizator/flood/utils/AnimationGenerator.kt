package com.fraktalizator.flood.utils

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

object AnimationGenerator {
    fun setUpAnimation(animationTexture: Texture?): ArrayList<Animation<TextureRegion>> {
        val animation = ArrayList<Animation<TextureRegion>>(5)
        val tmp = TextureRegion.split(animationTexture, 32, 48)
        val walkUpFrames = arrayOfNulls<TextureRegion>(4)
        val walkDownFrames = arrayOfNulls<TextureRegion>(4)
        val walkRightFrames = arrayOfNulls<TextureRegion>(4)
        val walkLeftFrames = arrayOfNulls<TextureRegion>(4)

        for (i in 0..3) {
            walkUpFrames[i] = tmp[3][i]
            walkRightFrames[i] = tmp[2][i]
            walkDownFrames[i] = tmp[0][i]
            walkLeftFrames[i] = tmp[1][i]
        }

        animation.add(Animation(0.125f, *walkUpFrames))
        animation.add(Animation(0.125f, *walkRightFrames))
        animation.add(Animation(0.125f, *walkDownFrames))
        animation.add(Animation(0.125f, *walkLeftFrames))
        animation.add(Animation(0.125f, *walkDownFrames))
        return animation
    }
}
