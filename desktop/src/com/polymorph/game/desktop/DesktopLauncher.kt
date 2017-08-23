package com.polymorph.game.desktop

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglFrame
import com.polymorph.game.PolymorphGame
import com.polymorph.game.io.FileLocations
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.ImageIcon
import javax.swing.WindowConstants

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val args = DesktopArgumentProcessor.process(arg)

        val config = LwjglApplicationConfiguration().apply {
            title = PolymorphGame.GAME_NAME
            useGL30 = true
        }
        val frame = LwjglFrame(PolymorphGame, config).apply {
            defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
            iconImage = ImageIcon(FileLocations.DataDirectory.resolve("blue_bandito_64.png").toUri().toURL()).image
            addWindowListener(DesktopFrameListener)
            maximize()
        }
    }
}

object DesktopFrameListener: WindowAdapter() {
    override fun windowClosing(e: WindowEvent?) {
        PolymorphGame.exit()
    }
}