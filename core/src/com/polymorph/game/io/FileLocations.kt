package com.polymorph.game.io

import com.badlogic.gdx.Gdx
import com.polymorph.game.PolymorphGame
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object FileLocations {
    val DataDirectory: Path = getRootDirectory()

    val ConfigDirectory: Path = DataDirectory.resolve("config")
    val LogConfigFile: Path = ConfigDirectory.resolve("log4j2.xml")

    private fun getRootDirectory(): Path = if (PolymorphGame.arguments.useCustomDataDirectory) {
        Paths.get(PolymorphGame.arguments.dataDirectory)
    } else {
        val jarPath = Paths.get(FileLocations::class.java.protectionDomain.codeSource.location.toURI())
        val folderPath = jarPath.parent
        val directDataPath = folderPath.resolve("data")
        directDataPath
    }
}