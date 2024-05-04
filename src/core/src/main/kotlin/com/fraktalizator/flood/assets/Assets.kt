package com.fraktalizator.flood.assets

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.scenes.scene2d.ui.Skin

object Assets {
    val SKIN_PATH = "cloud-form/skin/cloud-form-ui.json"

    private val assetManager: AssetManager = AssetManager()
    private val skinAssetDescriptor: AssetDescriptor<Skin>


    init {
        assetManager.setLoader(TiledMap::class.java, TmxMapLoader(InternalFileHandleResolver()))

        skinAssetDescriptor = AssetDescriptor(SKIN_PATH, Skin::class.java)
        assetManager.load(skinAssetDescriptor)

        // loads textures required before entering LoadingScreen. Loads in splash screen
        for (preGameTextureAssets in PreGameTextureAssets.entries) {
            assetManager.load(preGameTextureAssets.assetDescriptor)
        }
    }

    fun update() = assetManager.update()

    fun getProgress() = assetManager.progress

    fun queGameAssets() {
        for (textureAssets in TextureAssets.entries) {
            assetManager.load(textureAssets.assetDescriptor)
        }

        for (mapAssets in MapAssets.entries) {
            assetManager.load(mapAssets.assetDescriptor)
        }
    }

    val skin: Skin
        get() = assetManager.get(skinAssetDescriptor)

    enum class PreGameTextureAssets(private val path: String) {
        SETTINGS_WINDOW_BACKGROUND("Ui/SettingsWindow/SettingBackground.png"),
        SETTINGS_BUTTON_PRESSED("Ui/SettingsWindow/ButtonSettingsPress.png"),
        SETTINGS_BUTTON_NORMAL("Ui/SettingsWindow/ButtonSetting.png"),
        SETTINGS_SHOW_BUTTON("Ui/SettingsWindow/settings.png"),
        MenuBG("Ui/MenuScreen/BG.jpg");

        internal val assetDescriptor: AssetDescriptor<Texture> =
            AssetDescriptor(path, Texture::class.java)

        val texture: Texture
            get() = assetManager.get(assetDescriptor)
    }

    enum class TextureAssets(private val path: String) {
        PlayerMovementAnimation("player.png"),
        MoveTile("moveTile.png"),
        MoveTileSelected("moveTileSelected.png");

        val assetDescriptor: AssetDescriptor<Texture> =
            AssetDescriptor(path, Texture::class.java)

        val texture: Texture
            get() = assetManager.get(assetDescriptor)
    }

    enum class MapAssets(private val path: String) {
        TutorialIsland("tutorialZone/tutorialMain.tmx");

        val assetDescriptor: AssetDescriptor<TiledMap> = AssetDescriptor(path, TiledMap::class.java)

        val map: TiledMap
            get() = assetManager.get(assetDescriptor)
    }

    fun dispose() {
        assetManager.dispose()
    }
}
