package com.polymorph.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglFrame
import com.polymorph.game.PolymorphGame
import com.polymorph.game.io.FileLocations
import com.polymorph.game.io.Log
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.ImageIcon
import javax.swing.WindowConstants

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val args = DesktopArgumentProcessor.process(arg)
        PolymorphGame.arguments = args
        Log.info { args.toString() }

        val config = LwjglApplicationConfiguration().apply {
            title = PolymorphGame.GAME_NAME
            useGL30 = true
        }
        val frame = LwjglFrame(PolymorphGame, config).apply {
            defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
            iconImage = ImageIcon(FileLocations.dataDirectory.resolve("blue_bandito_64.png").toUri().toURL()).image
            addWindowListener(DesktopFrameListener)
            maximize()
        }

        Log.info { "Root directory: ${FileLocations.rootDirectory.toAbsolutePath()}" }
    }
}

object DesktopFrameListener: WindowAdapter() {
    override fun windowClosing(e: WindowEvent?) {
        PolymorphGame.exit()
    }
}