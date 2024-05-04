package com.mobgrinder.shadowcrest.settings.individual_settings_tables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.fraktalizator.flood.assets.Assets

class InterfaceSettings(private val interfaceSettingsData: HashMap<String, String>) : Table() {
    private val label = Label("Interface Settings", LabelStyle(Assets.skin.getFont("title"), Color.WHITE))
    private val skin = Assets.skin

    var inf: CheckBox? = null

    init {
        initSettings()
        initData()
        initTable()
    }

    private fun initSettings() {
        inf = CheckBox("interface", skin)
    }

    private fun initData() {
        inf!!.isChecked = interfaceSettingsData["inf"].toBoolean()
    }

    private fun initTable() {
        setDebug(false)
        add<Label>(label).expand().colspan(2)
        row()
        add<CheckBox>(inf).expand()
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

    fun getCurrentInterfaceSettingsData(): HashMap<String, String> {
        val interfaceSettingsData = HashMap<String, String>()
        interfaceSettingsData["inf"] = inf!!.isChecked.toString() + ""
        return interfaceSettingsData
    }
}
