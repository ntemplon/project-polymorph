package com.polymorph.game.io

import com.polymorph.game.PolymorphGame
import java.nio.file.Path
import java.nio.file.Paths

object FileLocations {

    val rootDirectory: Path = findRootDirectory()
    val dataDirectory: Path = rootDirectory.resolve("data")

    private fun findRootDirectory(): Path = if (PolymorphGame.arguments.useCustomRootDirectory) {
        Paths.get(PolymorphGame.arguments.rootDirectory).toAbsolutePath()
    } else {
        val jarPath = Paths.get(FileLocations::class.java.protectionDomain.codeSource.location.toURI())
        val folderPath = jarPath.parent
        folderPath
    }
}