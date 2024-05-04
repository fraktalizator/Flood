package com.fraktalizator.flood.screens.flood

import com.fraktalizator.flood.extension_methods.EnumExtensions.next
import com.fraktalizator.flood.GameWorld
import com.fraktalizator.flood.screens.BaseScreen.Companion.game
import com.fraktalizator.flood.systems.render.EntityRenderSystem
import com.fraktalizator.flood.systems.MovementSystem
import com.fraktalizator.flood.utils.Logger

class FloodScreenInitializer {
    private var task: InitializationTasks = InitializationTasks.entries[0]
    lateinit var floodScreen: FloodScreen
        private set

    fun loadingInfoString() = task.loadingInfoString//"Loading Assets..."

    fun progress() = task.ordinal.toFloat()/InitializationTasks.entries.size


    fun update(): Boolean {
        when (task){
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
            }

            InitializationTasks.DONE -> {
                return true
            }
        }
        task = task.next()
        return false
    }


    private fun loadMaps() {
        Thread.sleep(1000)
    }

    private fun loadSettingsAndEntities() {Thread.sleep(2000)}

    private fun initializeWorldMap() {Thread.sleep(1000)}

    private fun loadHud() {
        val gameWorld = GameWorld()
        gameWorld.initNPC()
        floodScreen = FloodScreen(gameWorld)
        val movementSystem = MovementSystem(gameWorld)
        gameWorld.engine.addSystem(EntityRenderSystem(game.mainBatch, floodScreen.camera))
        gameWorld.engine.addSystem(movementSystem)

    }
}


