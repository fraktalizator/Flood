package com.fraktalizator.flood.screens.flood

enum class InitializationTasks(val loadingInfoString:String){
    LOAD_MAPS("Loading maps..."),
    LOAD_SETTINGS_AND_ENTITIES("Loading settings and entities"),
    INITIALIZE_WORLD_MAP("Initializing world map"),
    LOAD_HUD("Loading HUD"),
    DONE("Done"),
}
