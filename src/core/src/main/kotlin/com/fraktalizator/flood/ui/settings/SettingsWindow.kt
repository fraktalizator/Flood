package com.fraktalizator.flood.ui.settings

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.fraktalizator.flood.Flood
import com.fraktalizator.flood.assets.Assets
import com.mobgrinder.shadowcrest.settings.SettingsButtons
import com.mobgrinder.shadowcrest.settings.individual_settings_tables.*

class SettingsWindow() : Window("", Assets.skin) {
    private val settingsFileManager = SettingsFileManager()
    private val bgTexture = Assets.PreGameTextureAssets.SETTINGS_WINDOW_BACKGROUND.texture
    private var currentPage = 0


    private var settingsButtons: SettingsButtons? = null
    var generalSettings: GeneralSettings? = null
    var interfaceSettings: InterfaceSettings? = null
    var audioSettings: AudioSettings? = null
    var keybindingsSettings: KeybindingsSettings? = null
    var videoSettings: VideoSettings? = null

    private val resetAllSettings = TextButton("Reset settings to default", Assets.skin)

    init {
        isMovable = false
        touchable = Touchable.enabled
        isVisible = true
        setBackground(TextureRegionDrawable(TextureRegion(bgTexture)))
        readSettingsDataFromFile()
        debug = true
        resetAllSettings.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                resetSettingsToDefault()
            }
        })

        settingsButtons = SettingsButtons(this)

        videoSettings!!.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                saveSettings()
            }
        })

        generalSettings!!.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                saveSettings()
            }
        })

        interfaceSettings!!.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                saveSettings()
            }
        })

        keybindingsSettings!!.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                saveSettings()
            }
        })

        audioSettings!!.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                saveSettings()
            }
        })
        setSize()
        hide()
    }

    fun toggle(){
        if(this.isVisible){
            hide()
        }else{
            show()
        }
    }

    private fun show() {
        this.isVisible = true
        toFront()
    }

    private fun hide() {
        this.isVisible = false
    }

    private fun addSettingsButtons() {
        add<SettingsButtons>(settingsButtons).expand().left().top()
            .width(width * 5 / 32)
            .height(height * 10 / 13)
            .padLeft(width * 10 / 284)
            .padTop(height * 12 / 178)
    }

    private fun addSettingsPage(settingTable: Table?) {
        add<Actor>(settingTable).expand().right().top()
            .width(width * 100 / 136)
            .height(height * 100 / 128)
            .padRight(width * 10 / 284)
            .padTop(height * 10 / 178)
    }

    fun setSettingsPage(i: Int) {

        when (i) {
            0 -> {
                clear()

                addSettingsButtons()

                addSettingsPage(generalSettings)

                currentPage = 0
            }

            1 -> {
                clear()

                addSettingsButtons()

                addSettingsPage(interfaceSettings)

                currentPage = 1
            }

            2 -> {
                clear()

                addSettingsButtons()

                addSettingsPage(videoSettings)

                currentPage = 2
            }

            3 -> {
                clear()

                addSettingsButtons()

                addSettingsPage(audioSettings)

                currentPage = 3
            }

            4 -> {
                clear()

                addSettingsButtons()

                addSettingsPage(keybindingsSettings)

                currentPage = 4
            }
        }
    }

    private fun saveSettings() {
        val settingsData = HashMap<String, HashMap<String, String>>()
        val generalSettingsData: HashMap<String, String> = generalSettings!!.GetCurrentGeneralSettingsData()
        val videoSettingsData: HashMap<String, String> = videoSettings!!.GetCurrentVideoSettingsData()
        val interfaceSettingsData: HashMap<String, String> = interfaceSettings!!.getCurrentInterfaceSettingsData()
        val keybindingsSettingsData: HashMap<String, String> = keybindingsSettings!!.getCurrentKeybindingsSettingsData()
        val audioSettingsData: HashMap<String, String> = audioSettings!!.getCurrentAudioSettingsData()
        settingsData["GeneralSettings"] = generalSettingsData
        settingsData["VideoSettings"] = videoSettingsData
        settingsData["InterfaceSettings"] = interfaceSettingsData
        settingsData["KeybindingsSettings"] = keybindingsSettingsData
        settingsData["AudioSettings"] = audioSettingsData
        settingsFileManager.saveSettings(settingsData)
    }

    private fun readSettingsDataFromFile() {
        val settingsDataFromFile = settingsFileManager.getSafeData()
        generalSettings = GeneralSettings(settingsDataFromFile["GeneralSettings"]!!, resetAllSettings)
        interfaceSettings = InterfaceSettings(settingsDataFromFile["InterfaceSettings"]!!)
        audioSettings = AudioSettings(settingsDataFromFile["AudioSettings"]!!)
        keybindingsSettings = KeybindingsSettings(settingsDataFromFile["KeybindingsSettings"]!!)
        videoSettings = VideoSettings(settingsDataFromFile["VideoSettings"]!!)
    }

    fun resetSettingsToDefault() {
        settingsFileManager.createDefaultSettings()
        readSettingsDataFromFile()
        setSettingsPage(currentPage)
    }

    fun size(): Vector2 {
        return Vector2(this.width, this.height)
    }

    private fun setSize() {
        setSize(Flood.VIEWPORT_WIDTH / 64 * 48, Flood.VIEWPORT_HEIGHT / 64 * 48)
        x = Flood.VIEWPORT_WIDTH / 2 - Flood.VIEWPORT_WIDTH / 64 * 48 / 2
        y = Flood.VIEWPORT_HEIGHT / 2 - Flood.VIEWPORT_HEIGHT / 64 * 48 / 2
        setBounds(x, y, width, height)
        //settingsButtons!!.resize()
        setSettingsPage(currentPage)
    }
}
