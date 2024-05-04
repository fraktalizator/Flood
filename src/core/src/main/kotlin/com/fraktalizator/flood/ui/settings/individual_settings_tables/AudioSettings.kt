package com.mobgrinder.shadowcrest.settings.individual_settings_tables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.fraktalizator.flood.assets.Assets

class AudioSettings(private val audioSettingsData: HashMap<String, String>) : Table() {
    private val label = Label("Audio Settings", LabelStyle(Assets.skin.getFont("title"), Color.WHITE))
    private val skin = Assets.skin
    var audio: CheckBox? = null

    init {
        initSettings()
        initData()
        initTable()
    }

    private fun initSettings() {
        audio = CheckBox("audio", skin)
    }

    private fun initData() {
        audio!!.isChecked = audioSettingsData["audio"].toBoolean()
    }


    private fun initTable() {
        debug = false
        add(label).expand().colspan(2)
        row()
        add(audio).expand()
        add().expand()
        row()
        add().expand()
        add().expand()
        row()
        add().expand()
        add().expand()
        row()
        add().expand()
        add().expand()
        row()
        add().expand()
        add().expand()
        row()
        add().expand()
        add().expand()
        row()
        add().expand()
        add().expand()
    }

    fun getCurrentAudioSettingsData(): HashMap<String, String> {
        val audioSettingsData = HashMap<String, String>()
        audioSettingsData["audio"] = audio!!.isChecked.toString() + ""
        return audioSettingsData
    }
}
