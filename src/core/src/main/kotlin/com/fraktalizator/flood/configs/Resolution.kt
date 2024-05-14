package com.fraktalizator.flood.configs

import com.badlogic.gdx.math.Vector2

enum class Resolution(val width: Int, val height: Int) {
    FULL_HD(1920, 1080),
    HD(1280, 720),
    FOUR_K(FULL_HD.width * 2, FULL_HD.height * 2),
    Q_HD(1440, 900);

    val size: Vector2 = Vector2(width.toFloat(), height.toFloat())

    override fun toString() = "Resolution of $width x $height"

    companion object {
        fun getResolutionBySize(width: Int, height: Int): Resolution {
            for (res in Resolution.entries) {
                if (res.width == width && res.height == height) return res
            }
            return FULL_HD
        }

        fun getResolutionBySize(str: String): Resolution {
            val indexOfx = str.indexOf("x")
            val width = str.substring(0, indexOfx).toIntOrNull()
            val height = str.substring(indexOfx + 1, str.length).toIntOrNull()
            if (width == null || height == null) return FULL_HD

            return getResolutionBySize(width, height)
        }
    }
}
