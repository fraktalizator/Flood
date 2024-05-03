package com.fraktalizator.flood.configs

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

    fun update(): Boolean {
        return assetManager.update()
    }

    fun getProgress():Float{
        return assetManager.progress
    }

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
        MenuBG("Ui/MenuScreen/BG.jpg");

//        SettingsWindowBG("Ui/SettingsWindow/SettingBackround.png"),
//
//        SaveSelectWindowBG("Ui/SaveScreen/saveButtons/selectlevelbackground.png"),
//        SaveButton("Ui/SaveScreen/saveButtons/save.png"),
//        SaveButtonSelected("Ui/SaveScreen/saveButtons/saveclick.png"),
//
//        SaveCreateWindowBG("Ui/CharacterCreateScreen/CreatorBackground.png"),
//        CharacterPreviewBG("Ui/CharacterCreateScreen/CharacterPreviewBackground.png"),
//        CharacterPreviewBGBorder("Ui/CharacterCreateScreen/CharacterPreviewBackgroundBorder.png"),
//        ClassButton("Ui/CharacterCreateScreen/SelectClass.png"),
//        ClassButtonSelected("Ui/CharacterCreateScreen/SelectClassClick146x207.png"),
//
//        CustomizeOptionBackButton("Ui/CharacterCreateScreen/ArrowButton.png"),
//        CustomizeOptionBackButtonSelected("Ui/CharacterCreateScreen/ArrowButtonClick.png"),
//
//        CustomizeOptionNextButton("Ui/CharacterCreateScreen/ArrowButtonL.png"),
//
//        CustomizeOptionNextButtonSelected("Ui/CharacterCreateScreen/ArrowButtonClickL.png"),
//
//        CustomizeOptionLabelBackground("Ui/CharacterCreateScreen/StyleText.png");

        internal val assetDescriptor: AssetDescriptor<Texture> =
            AssetDescriptor(path, Texture::class.java)

        val texture: Texture
            get() = assetManager.get(assetDescriptor)
    }

    enum class TextureAssets(private val path: String) {
        PlayerMovementAnimation("player.png"),
//        Stairs("Actors/Interactable/stairs.png"),
//        InventorySlot("Ui/GameScreen/Hud/Inveontry/eqgrid.png"),
//        BadApple("Items/Consumable/bad_apple.png"),
        MoveTile("moveTile.png"),
        MoveTileSelected("moveTileSelected.png");
//        HeavyArm("Spells/Warrior/Talents/heavyArm.png"),
//        HerbPouch("Spells/Warrior/Talents/heavyArm.png"),
//        InventoryBG("Ui/GameScreen/Hud/Inveontry/frameeqFilled.png");

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
