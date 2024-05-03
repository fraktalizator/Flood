package com.fraktalizator.flood.screens.flood

import com.fraktalizator.flood.game_objects.GameWorld
import com.fraktalizator.flood.screens.AbstractScreen.Companion.game
import com.fraktalizator.flood.systems.EntityRenderSystem
import com.fraktalizator.flood.systems.MovementSystem
import com.fraktalizator.flood.utils.Logger

class FloodScreenInitializer {
    private var task: InitializationTask = InitializationTask.entries[0]
    lateinit var floodScreen: FloodScreen
        private set

    fun loadingInfoString() = task.loadingInfoString//"Loading Assets..."

    fun progress() = task.ordinal.toFloat()/InitializationTask.entries.size


    fun update(): Boolean {
        when (task){
            InitializationTask.LOAD_MAPS -> {
                Logger.log("loading maps")
                loadMaps()
            }
            InitializationTask.LOAD_SETTINGS_AND_ENTITIES -> {
                Logger.log("loading maps and entities")
                loadSettingsAndEntities()
            }
            InitializationTask.INITIALIZE_WORLD_MAP -> {
                Logger.log("Initializing world")
                initializeWorldMap()
            }
            InitializationTask.LOAD_HUD -> {
                Logger.log("Loading HUD")
                loadHud()
            }
        }
        val isLoadingDone = task.ordinal == InitializationTask.entries.size-1
        if(!isLoadingDone) task = task.next()
        return isLoadingDone
    }


    private fun loadMaps() {}

    private fun loadSettingsAndEntities() {}

    private fun initializeWorldMap() {}

    private fun loadHud() {
        val gameWorld = GameWorld()
        gameWorld.initNPC()
        floodScreen = FloodScreen(gameWorld)
        val movementSystem = MovementSystem(gameWorld)
        gameWorld.engine.addSystem(EntityRenderSystem(game.mainBatch, floodScreen.camera))
        gameWorld.engine.addSystem(movementSystem)

    }
}

private enum class InitializationTask(val loadingInfoString:String){
    LOAD_MAPS("Loading maps..."),
    LOAD_SETTINGS_AND_ENTITIES("Loading settings and entities"),
    INITIALIZE_WORLD_MAP("Initializing world map"),
    LOAD_HUD("Loading HUD"),
}

inline fun <reified T: Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values[nextOrdinal]
}

