package com.polymorph.game.desktop

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl.LwjglFrame
import com.polymorph.game.PolymorphGame
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.WindowConstants

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val args = DesktopArgumentProcessor.process(arg)

        val config = LwjglApplicationConfiguration().apply {

        }
        val frame = LwjglFrame(PolymorphGame, config)
        frame.defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        frame.addWindowListener(DesktopFrameListener)
    }
}

object DesktopFrameListener: WindowAdapter() {
    override fun windowClosing(e: WindowEvent?) {
        PolymorphGame.exit()
    }
}