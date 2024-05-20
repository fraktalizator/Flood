package com.fraktalizator.flood.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.ui.settings.SettingsWindow

class SettingsShowButton(
    private val settingsWindow: SettingsWindow
) : ImageButton(TextureRegionDrawable(Assets.PreGameTextureAssets.SETTINGS_SHOW_BUTTON.texture)) {
    init {
        width = 64f
        height = 64f
        setBounds(x, y, width, height)
        addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                settingsWindow.toggle()
                return super.touchDown(event, x, y, pointer, button)
            }
        })
    }

    override fun positionChanged() {
        setBounds(x, y, width, height)
    }
}
