package com.mobgrinder.shadowcrest.settings

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.ui.settings.SettingsWindow

class SettingsButtons(settingsWindow: SettingsWindow) : Table() {
    private val generalBTN: SettingsButton = SettingsButton("General")
    private val interfaceBTN: SettingsButton = SettingsButton("Interface")
    private val videoBTN: SettingsButton = SettingsButton("Video")
    private val audioBTN: SettingsButton = SettingsButton("Audio")
    private val keybindingsBTN: SettingsButton = SettingsButton("Key binds")
    val backBTN: SettingsButton = SettingsButton("Back")

    init {
        val buttonGroup = ButtonGroup<SettingsButton>()

        buttonGroup.add(generalBTN)
        buttonGroup.add(interfaceBTN)
        buttonGroup.add(videoBTN)
        buttonGroup.add(audioBTN)
        buttonGroup.add(keybindingsBTN)
        buttonGroup.setUncheckLast(true)

        generalBTN.isChecked = true

        fun setPage(pageIndex: Int): ClickListener {
            return object : ClickListener() {
                override fun clicked(event: InputEvent, x: Float, y: Float) {
                    settingsWindow.setSettingsPage(pageIndex)
                }
            }
        }

        generalBTN.addListener(setPage(0))
        interfaceBTN.addListener(setPage(1))
        videoBTN.addListener(setPage(2))
        audioBTN.addListener(setPage(3))
        keybindingsBTN.addListener(setPage(4))

        backBTN.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                settingsWindow.isVisible = false
                backBTN.setChecked(false)
                //generalBTN.setChecked(true);
            }
        })

        setUpTable()
    }


    private fun setUpTable() {
        debug = false
        val padValue = 1 * Flood.HEIGHT_SCALE
        add(generalBTN).width(generalBTN.width).height(generalBTN.height).padTop(padValue).padBottom(padValue)
        row()
        add(interfaceBTN).width(interfaceBTN.width).height(interfaceBTN.height).padTop(padValue).padBottom(padValue)
        row()
        add(videoBTN).width(videoBTN.width).height(videoBTN.height).padTop(padValue).padBottom(padValue)
        row()
        add(audioBTN).width(audioBTN.width).height(audioBTN.height).padTop(padValue).padBottom(padValue)
        row()
        add(keybindingsBTN).width(keybindingsBTN.width).height(keybindingsBTN.height).padTop(padValue)
            .padBottom(padValue)
        row()
        add(backBTN).width(backBTN.width).height(backBTN.height).padTop(padValue).padBottom(padValue)
        row()

        width = generalBTN.width

        height = generalBTN.height * 6 + padValue * 6 * 2
    }


    class SettingsButton(text: String) : TextButton(text, Assets.skin) {
        private val btnUP = Assets.PreGameTextureAssets.SETTINGS_BUTTON_NORMAL.texture
        private val btnDOWN = Assets.PreGameTextureAssets.SETTINGS_BUTTON_PRESSED.texture
        private val btnCHECKED = Assets.PreGameTextureAssets.SETTINGS_BUTTON_PRESSED.texture

        init {
            name = text
            touchable = Touchable.enabled
            val bitmapFont = Assets.skin.getFont("title")
            val textButtonStyle = TextButtonStyle(
                TextureRegionDrawable(btnUP),
                TextureRegionDrawable(btnDOWN),
                TextureRegionDrawable(btnCHECKED),
                bitmapFont
            )
            textButtonStyle.over = TextureRegionDrawable(btnDOWN)
            setStyle(textButtonStyle)

            width = 7.6f * Flood.WIDTH_SCALE

            height = 4.7f * Flood.HEIGHT_SCALE
        }
    }

}
