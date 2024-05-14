package com.fraktalizator.flood.screens.flood

import com.badlogic.gdx.utils.Logger
import com.fraktalizator.flood.ashley.WorldEngineInitializer
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.extension_methods.next
import com.fraktalizator.flood.ui.settings.SettingsWindow

class FloodScreenInitializer {
    private val logger = Logger("init")
    private var task: InitializationTasks = InitializationTasks.entries[0]
    lateinit var floodScreen: FloodScreen
        private set

    fun loadingInfoString() = task.loadingInfoString//"Loading Assets..."

    fun progress() = task.ordinal.toFloat() / InitializationTasks.entries.size

    fun update(): Boolean {
        when (task) {
            InitializationTasks.LOAD_MAPS -> {
                logger.info("loading maps")
                loadMaps()
            }

            InitializationTasks.LOAD_SETTINGS_AND_ENTITIES -> {
                logger.info("loading maps and entities")
                loadSettingsAndEntities()
            }

            InitializationTasks.INITIALIZE_WORLD_MAP -> {
                logger.info("Initializing world")
                initializeWorldMap()
            }

            InitializationTasks.LOAD_HUD -> {
                logger.info("Loading HUD")
                loadHud()
                val worldEngineInitializer = WorldEngineInitializer(Assets.MapAssets.TutorialIsland.map)
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


