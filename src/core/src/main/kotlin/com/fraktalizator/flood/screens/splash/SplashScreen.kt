package com.fraktalizator.flood.screens.splash

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.ScreenUtils
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.screens.loading.LoadingScreen
import ktx.app.KtxScreen

class SplashScreen : KtxScreen {
    private val splashSprite = Sprite(Texture(Gdx.files.internal("Ui/splash.png")))
    private val splashTimerInSecs = 1.15f
    private var elapsedTime = 0f


    init {
        splashSprite.setSize(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    }

    override fun render(delta: Float) {
        //System.out.println(Gdx.graphics.getFramesPerSecond()+" FPS");
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        elapsedTime += delta

        Flood.mainBatch.begin()
        if (elapsedTime / splashTimerInSecs < 1) {
            splashSprite.draw(
                Flood.mainBatch,
                (1 - elapsedTime / splashTimerInSecs)
            )
        }
        Flood.mainBatch.end()

        if (!Assets.update()) return


        if (elapsedTime >= splashTimerInSecs) {
            Flood.addScreen(LoadingScreen())
            Flood.setScreen<LoadingScreen>()
            dispose()
        }
    }


    override fun dispose() {
        splashSprite.texture.dispose()
    }
}
