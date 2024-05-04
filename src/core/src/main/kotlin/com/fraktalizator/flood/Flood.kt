package com.fraktalizator.flood

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.screens.splash.SplashScreen
import ktx.app.KtxGame
import ktx.app.KtxScreen

object Flood : KtxGame<KtxScreen>() {
    const val WIDTH_SCALE = 16f
    const val HEIGHT_SCALE = 9f
    const val VIEWPORT_WIDTH = 64 * WIDTH_SCALE
    const val VIEWPORT_HEIGHT = 64 * HEIGHT_SCALE

    lateinit var mainBatch: SpriteBatch


    override fun create() {
        Gdx.app.logLevel = Application.LOG_DEBUG
        mainBatch = SpriteBatch()

        addScreen(SplashScreen())
        setScreen<SplashScreen>()
    }

    override fun dispose() {
        mainBatch.dispose()
        Assets.dispose()
    }
}
