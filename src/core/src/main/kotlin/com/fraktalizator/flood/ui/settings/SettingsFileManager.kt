package com.fraktalizator.flood.ui.settings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.JsonValue
import com.badlogic.gdx.utils.JsonWriter
import com.fraktalizator.flood.configs.Resolution

class SettingsFileManager {
    var json = Json(JsonWriter.OutputType.json)
    var settingsFile = Gdx.files.absolute("RPGGame/settings.json")


    fun saveSettings(settingsData: HashMap<String, HashMap<String, String>>) {
        val dataJsonString = json.toJson(settingsData)
        settingsFile.writeString(dataJsonString, false)
    }

    fun getSafeData(): HashMap<String, HashMap<String, String>> {
        var settingsData: HashMap<String, HashMap<String, String>>
        //TODO costam z exceptionami lepiej mozna by zrobic a nie nullpointer
        try {
            settingsData = getUnsafeSettingsData()
            val generalSettingsData = settingsData["GeneralSettings"]!!
            val videoSettingsData = settingsData["VideoSettings"]!!
            val interfaceSettingsData = settingsData["InterfaceSettings"]!!
            val keybindingsSettingsData = settingsData["KeybindingsSettings"]!!
            val audioSettingsData = settingsData["AudioSettings"]!!

            // ---------------- GENERAL SETTINGS CHECK ---------------- //
            if (generalSettingsData["AutoLoot"] == null) throw NullPointerException()
            generalSettingsData["AutoLoot"].toBoolean()
            if (generalSettingsData["AutoSave"] == null) throw NullPointerException()
            generalSettingsData["AutoSave"].toBoolean()
            if (generalSettingsData["ScrollCombatText"] == null) throw NullPointerException()
            generalSettingsData["ScrollCombatText"].toBoolean()


            // ---------------- VIDEO SETTINGS CHECK ---------------- //
            videoSettingsData["ResolutionWidth"]!!.toInt()
            videoSettingsData["ResolutionHeight"]!!.toInt()
            if (videoSettingsData["FullScreen"] == null) throw NullPointerException()
            videoSettingsData["FullScreen"].toBoolean()

            // ---------------- INTERFACE SETTINGS CHECK ---------------- //
            if (interfaceSettingsData["inf"] == null) throw NullPointerException()
            interfaceSettingsData["inf"].toBoolean()


            // ---------------- KEYBINDINGS SETTINGS CHECK ---------------- //
            if (keybindingsSettingsData["bind"] == null) throw NullPointerException()
            keybindingsSettingsData["bind"].toBoolean()

            // ---------------- AUDIO SETTINGS CHECK ---------------- //
            if (audioSettingsData["audio"] == null) throw NullPointerException()
            audioSettingsData["audio"].toBoolean()
        } catch (e: Exception) {
            e.printStackTrace()
            createNecessaryDirectory()
            createDefaultSettings()
            settingsData = getUnsafeSettingsData()
        }
        return settingsData
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(TypeCastException::class)
    private fun getUnsafeSettingsData(): HashMap<String, HashMap<String, String>> {
        val rawFileData = settingsFile.readString()
        val unConvertedData: HashMap<String, JsonValue> =
            json.fromJson(HashMap::class.java, rawFileData) as HashMap<String, JsonValue>
        val generalSettings: HashMap<String, String> = json.readValue(
            HashMap::class.java,
            String::class.java,
            unConvertedData["GeneralSettings"]
        ) as HashMap<String, String>
        val interfaceSettings: HashMap<String, String> = json.readValue(
            HashMap::class.java,
            String::class.java,
            unConvertedData["InterfaceSettings"]
        ) as HashMap<String, String>
        val videoSettings: HashMap<String, String> = json.readValue(
            HashMap::class.java,
            String::class.java,
            unConvertedData["VideoSettings"]
        ) as HashMap<String, String>
        val keybindingsSettings: HashMap<String, String> = json.readValue(
            HashMap::class.java,
            String::class.java,
            unConvertedData["KeybindingsSettings"]
        ) as HashMap<String, String>
        val audioSettings: HashMap<String, String> = json.readValue(
            HashMap::class.java,
            String::class.java,
            unConvertedData["AudioSettings"]
        ) as HashMap<String, String>
        val data = HashMap<String, HashMap<String, String>>()
        data["GeneralSettings"] = generalSettings
        data["VideoSettings"] = videoSettings
        data["InterfaceSettings"] = interfaceSettings
        data["KeybindingsSettings"] = keybindingsSettings
        data["AudioSettings"] = audioSettings
        return data
    }


    fun createDefaultSettings() {
        createNecessaryDirectory()
        saveSettings(getDefaultSettings())
    }

    private fun getDefaultSettings(): HashMap<String, HashMap<String, String>> {
        val settingsData = HashMap<String, HashMap<String, String>>()
        val generalSettingsData = HashMap<String, String>()
        val videoSettingsData = HashMap<String, String>()
        val interfaceSettingsData = HashMap<String, String>()
        val keybindingsSettingsData = HashMap<String, String>()
        val audioSettingsData = HashMap<String, String>()
        generalSettingsData["AutoLoot"] = "false"
        generalSettingsData["AutoSave"] = "true"
        generalSettingsData["ScrollCombatText"] = "false"
        videoSettingsData["ResolutionWidth"] = Resolution.HD.width.toString()
        videoSettingsData["ResolutionHeight"] = Resolution.HD.height.toString()
        videoSettingsData["FullScreen"] = "false"
        interfaceSettingsData["inf"] = "false"
        keybindingsSettingsData["bind"] = "false"
        audioSettingsData["audio"] = "false"
        settingsData["GeneralSettings"] = generalSettingsData
        settingsData["VideoSettings"] = videoSettingsData
        settingsData["InterfaceSettings"] = interfaceSettingsData
        settingsData["KeybindingsSettings"] = keybindingsSettingsData
        settingsData["AudioSettings"] = audioSettingsData
        return settingsData
    }

    private fun createNecessaryDirectory() {
        val mainDir = Gdx.files.absolute("RPGGame")
        //FileHandle settings = new FileHandle("RPGGame/settings.json");
        val settingsFile =
            Gdx.files.absolute("RPGGame/settings.json") //new FileHandle(Gdx.files.getExternalStoragePath()+"/RPGGame/settings.json");
        val keyBindingsFile =
            Gdx.files.absolute("RPGGame/keyBindings.json") //new FileHandle(Gdx.files.getExternalStoragePath()+"/RPGGame/keyBindings.json");
        val playersDir =
            Gdx.files.absolute("RPGGame/players") //new FileHandle(Gdx.files.getExternalStoragePath()+"/RPGGame/players");
        if (!mainDir.exists()) mainDir.mkdirs()
        if (!settingsFile.exists()) {
            settingsFile.write(false)
        }
        if (!keyBindingsFile.exists()) keyBindingsFile.write(false)
        if (!playersDir.exists()) playersDir.mkdirs()
    }
}
