package com.mobgrinder.shadowcrest.settings.individual_settings_tables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.fraktalizator.flood.assets.Assets

class KeybindingsSettings(private val keybindingsSettingsData: HashMap<String, String>) : Table() {
    private val label = Label("Keybindings Settings", LabelStyle(Assets.skin.getFont("title"), Color.WHITE))
    private val skin = Assets.skin
    var bind: CheckBox? = null

    init {
        initSettings()
        initData()
        initTable()
    }

    private fun initSettings() {
        bind = CheckBox("bind", skin)
    }

    private fun initData() {
        bind!!.isChecked = keybindingsSettingsData!!["bind"].toBoolean()
    }

    private fun initTable() {
        setDebug(false)
        add<Label>(label).expand().colspan(2)
        row()
        add<CheckBox>(bind).expand()
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

    fun getCurrentKeybindingsSettingsData(): HashMap<String, String> {
        val keybindingsSettingsData = HashMap<String, String>()
        keybindingsSettingsData["bind"] = bind!!.isChecked.toString() + ""
        return keybindingsSettingsData
    }
}
