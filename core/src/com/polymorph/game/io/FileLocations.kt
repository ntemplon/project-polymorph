package com.polymorph.game.io

import com.badlogic.gdx.Gdx
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

object FileLocations {
    val DataDirectory: Path = getRootDirectory()

    private fun getRootDirectory(): Path {
        val jarPath = Paths.get(FileLocations::class.java.protectionDomain.codeSource.location.toURI())
        val folderPath = jarPath.parent
        val directDataPath = folderPath.resolve("data")

        return if (directDataPath.exists) {
            // We are deployed
            directDataPath
        } else {
            // We are in the editor
            folderPath.resolve("../../data")
        }
    }
}