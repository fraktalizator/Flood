package com.mobgrinder.shadowcrest.settings.individual_settings_tables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.fraktalizator.flood.assets.Assets

class GeneralSettings(private val generalSettingsData: HashMap<String, String>, resetAllSettings: TextButton) :
    Table() {
    private val label = Label("General Settings", LabelStyle(Assets.skin.getFont("title"), Color.WHITE))
    private val skin = Assets.skin

    var autoSave: CheckBox? = null
    var autoLoot: CheckBox? = null
    var scrollCombatText: CheckBox? = null

    init {
        initSettings()
        initData()
        initTable(resetAllSettings)
    }

    private fun initData() {
        autoLoot!!.isChecked = generalSettingsData["AutoLoot"].toBoolean()
        autoSave!!.isChecked = generalSettingsData["AutoSave"].toBoolean()
        scrollCombatText!!.isChecked = generalSettingsData["ScrollCombatText"].toBoolean()
    }

    private fun initSettings() {
        autoSave = CheckBox("Enables auto Save On(auto save after every won battle(up to 3 auto saves))", skin)
        autoLoot = CheckBox("Enables auto Loot, if enough place in inventory", skin)
        scrollCombatText = CheckBox("Displays out of combat heal and damage text", skin)
    }

    private fun initTable(resetAllSettings: TextButton) {
        setDebug(false)
        add<Label>(label).expand().colspan(2)
        row()
        add<CheckBox>(autoSave).expand().colspan(2)
        row()
        add<CheckBox>(autoLoot).expand().colspan(2)
        row()
        add<CheckBox>(scrollCombatText).expand().colspan(2)
        row()
        add().expand().colspan(2)
        row()
        add().expand().colspan(2)
        row()
        add().expand().colspan(2)
        row()
        add().expand()
        add<TextButton>(resetAllSettings).expand().left()
    }

    fun GetCurrentGeneralSettingsData(): HashMap<String, String> {
        val generalSettingsData = HashMap<String, String>()
        generalSettingsData["AutoLoot"] = autoLoot!!.isChecked.toString()
        generalSettingsData["AutoSave"] = autoSave!!.isChecked.toString()
        generalSettingsData["ScrollCombatText"] = scrollCombatText!!.isChecked.toString()
        return generalSettingsData
    }
}
