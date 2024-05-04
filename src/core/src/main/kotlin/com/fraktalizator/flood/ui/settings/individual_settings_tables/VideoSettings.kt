package com.mobgrinder.shadowcrest.settings.individual_settings_tables

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Pools
import com.fraktalizator.flood.assets.Assets
import com.fraktalizator.flood.configs.Resolution

class VideoSettings//fire(Pools.obtain(ChangeListener.ChangeEvent.class));
    (private val videoSettingsData: HashMap<String, String>) : Table() {
    private val label = Label("Video Settings", LabelStyle(Assets.skin.getFont("title"), Color.WHITE))
    private val skin = Assets.skin

    private var fullScreenBTN: TextButton? = null
    private var resolutions: SelectBox<String>? = null

    var resolution: Resolution? = null

    init {
        initSettings()
        initData()
        initTable()
    }

    private fun initData() {
        resolution = Resolution.getResolutionBySize(
            videoSettingsData["ResolutionWidth"]!!.toInt(),
            videoSettingsData["ResolutionHeight"]!!.toInt()
        )
        val isFullScreen = videoSettingsData["FullScreen"].toBoolean()
        if (isFullScreen) {
            fullScreenBTN!!.setText("Windowed")
            Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
        } else {
            fullScreenBTN!!.setText("Full Screen")
            Gdx.graphics.setWindowedMode(resolution!!.width, resolution!!.height)
        }


//        for (String resItem: resolutions.getItems()){
//            if (resolution.toString().equals(resItem)){
//                resolutions.setSelected(resItem);
//            }
//        }
        resolutions!!.selected = resolution.toString()
    }

    private fun initSettings() {
        fullScreenBTN = TextButton("Full Screen", skin)
        fullScreenBTN!!.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                if (Gdx.graphics.isFullscreen) {
                    Gdx.graphics.setWindowedMode(resolution!!.width, resolution!!.height)
                    fullScreenBTN!!.setText("Full Screen")
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
                    fullScreenBTN!!.setText("Windowed")
                }

            }
        })
        resolutions = SelectBox(skin)
        val resArray = arrayOfNulls<String>(Resolution.entries.size)
        var iterator = 0
        for (res in Resolution.entries) {
            val temp = res.toString()
            resArray[iterator] = temp
            iterator++
        }
        resolutions!!.setItems(*resArray)
        resolutions!!.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                resolution = Resolution.getResolutionBySize(resolutions!!.selected)
                Gdx.app.applicationListener.resize(Gdx.graphics.width, Gdx.graphics.height)
                //TODO save settings
            }
        })
    }

    private fun initTable() {
        setDebug(false)
        add<Label>(label).expand().colspan(2)
        row()
        add<TextButton>(fullScreenBTN).expand()
        add<SelectBox<String>>(resolutions).expand()
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

    fun GetCurrentVideoSettingsData(): HashMap<String, String> {
        val videoSettingsData = HashMap<String, String>()
        videoSettingsData["ResolutionWidth"] = resolution!!.width.toString() + ""
        videoSettingsData["ResolutionHeight"] = resolution!!.height.toString() + ""
        println(fullScreenBTN!!.text.toString() == "Windowed")
        println(fullScreenBTN!!.text.toString().javaClass)
        val isFullScreen = fullScreenBTN!!.text.toString() == "Windowed"
        videoSettingsData["FullScreen"] = isFullScreen.toString() + ""
        //TODO fullscren data is wrong maybe  changelistener odpala sie szybciej niz fullscreen button click
        return videoSettingsData
    }

    fun fireChangeEvent() {
        val changeEvent = Pools.obtain(ChangeListener.ChangeEvent::class.java)
        try {
            fire(changeEvent)
        } finally {
            Pools.free(changeEvent)
        }
    }

}
