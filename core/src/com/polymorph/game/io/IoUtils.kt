package com.polymorph.game.io

import java.nio.file.DirectoryStream
import java.nio.file.Files
import java.nio.file.Path

val Path.exists: Boolean
    get() = Files.exists(this)
val Path.isDirectory: Boolean
    get() = Files.isDirectory(this)

fun Path.directoryStream(): DirectoryStream<Path> = Files.newDirectoryStream(this)
fun Path.onContents(op: (Path) -> Unit): Unit = this.directoryStream().use { it.forEach(op) }
