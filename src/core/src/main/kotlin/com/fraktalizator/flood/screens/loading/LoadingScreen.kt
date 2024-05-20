package com.fraktalizator.flood.screens.loading

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar
import com.badlogic.gdx.utils.viewport.Viewport
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.screens.BaseScreen
import com.fraktalizator.flood.screens.flood.FloodScreen
import com.fraktalizator.flood.screens.flood.FloodScreenInitializer

class LoadingScreen : BaseScreen<Stage>({ viewport: Viewport, batch: Batch -> Stage(viewport, batch) }) {
    private val backgroundSprite: Sprite
    private val progressBar: ProgressBar
    private var floodScreenInitializer = FloodScreenInitializer()

    init {
        Assets.queGameAssets()
        Gdx.input.inputProcessor = stage

        backgroundSprite = Sprite(Texture(Gdx.files.internal("Ui/MenuScreen/BG.jpg")))

        //create loading progress bar
        progressBar = ProgressBar(0f, 1f, 0.01f, false, Assets.skin)
        progressBar.width = 300f
        progressBar.height = 30f
        val scrCenterForProgressBar = getScreenCenterFor(progressBar.width, progressBar.height)
        progressBar.setPosition(
            scrCenterForProgressBar.x,
            scrCenterForProgressBar.y
        )
        stage.addActor(progressBar)
    }


    override fun update(delta: Float) = load()

    override fun draw(delta: Float) {
        backgroundSprite.draw(stage.batch)

        skin.getFont("regular").draw(
            bach,
            floodScreenInitializer.loadingInfoString(),
            progressBar.x,
            progressBar.y
        )
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
