package com.fraktalizator.flood.screens.loading

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.fraktalizator.flood.configs.Assets
import com.fraktalizator.flood.screens.AbstractScreen
import com.fraktalizator.flood.screens.flood.FloodScreen
import com.fraktalizator.flood.screens.flood.FloodScreenInitializer

class LoadingScreen : AbstractScreen() {
    private val backgroundSprite: Sprite
    private val progressBar: ProgressBar
    private var floodScreenInitializer = FloodScreenInitializer()

    init {
        Assets.queGameAssets()
        Gdx.input.inputProcessor = stage

        backgroundSprite = Sprite(Texture(Gdx.files.internal("Ui/MenuScreen/BG.jpg")))

        progressBar = ProgressBar(0f, 1f, 0.01f, false, Assets.skin)
        progressBar.width = 300f
        progressBar.height = 30f
        progressBar.setPosition(
            Gdx.graphics.width / 2f - progressBar.width / 2,
            Gdx.graphics.height / 2.5f - progressBar.height / 2
        )
        stage.addActor(progressBar)
    }


    override fun update(delta: Float) {
        load()

        bach.begin()
        backgroundSprite.draw(stage.batch)
        val scrCenterForProgressBar = getScreenCenterFor(progressBar.width, progressBar.height)
        skin.getFont("antek").draw(
            bach,
            floodScreenInitializer.loadingInfoString(),
            scrCenterForProgressBar.x,
            scrCenterForProgressBar.y
        )
        bach.end()
    }

    private fun load() {
        if (Assets.update()) {
            if (floodScreenInitializer.update()) {
                game.addScreen(floodScreenInitializer.floodScreen)
                game.setScreen<FloodScreen>()
                dispose()
            } else progressBar.setValue(0.5f + (floodScreenInitializer.progress() / 2))
        } else progressBar.setValue(Assets.getProgress() / 2)
    }

    override fun dispose() {
        super.dispose()
        backgroundSprite.texture.dispose()
    }
}
