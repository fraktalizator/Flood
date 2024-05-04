package com.fraktalizator.flood.screens.flood

import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.entityEngine.WorldEngineInitializer
import com.fraktalizator.flood.extension_methods.EnumExtensions.next
import com.fraktalizator.flood.ui.settings.SettingsWindow
import com.fraktalizator.flood.utils.Logger

class FloodScreenInitializer {
    private var task: InitializationTasks = InitializationTasks.entries[0]
    lateinit var floodScreen: FloodScreen
        private set

    fun loadingInfoString() = task.loadingInfoString//"Loading Assets..."

    fun progress() = task.ordinal.toFloat() / InitializationTasks.entries.size

    fun update(): Boolean {
        when (task) {
            InitializationTasks.LOAD_MAPS -> {
                Logger.log("loading maps")
                loadMaps()
            }

            InitializationTasks.LOAD_SETTINGS_AND_ENTITIES -> {
                Logger.log("loading maps and entities")
                loadSettingsAndEntities()
            }

            InitializationTasks.INITIALIZE_WORLD_MAP -> {
                Logger.log("Initializing world")
                initializeWorldMap()
            }

            InitializationTasks.LOAD_HUD -> {
                Logger.log("Loading HUD")
                loadHud()
                val worldEngineInitializer = WorldEngineInitializer(Assets.MapAssets.TutorialIsland.map)
                worldEngineInitializer.initNPC()
                floodScreen = FloodScreen(worldEngineInitializer, SettingsWindow())
            }

            InitializationTasks.DONE -> {
                return true
            }
        }
        task = task.next()
        return false
    }


    private fun loadMaps() {
        //Thread.sleep(1000)//TODO
    }

    private fun loadSettingsAndEntities() {}//Thread.sleep(2000)}//TODO

    private fun initializeWorldMap() {}//Thread.sleep(1000)}//TODO

    private fun loadHud() {}//TODO
}


