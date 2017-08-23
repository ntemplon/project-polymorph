package com.polymorph.game.io

import java.nio.file.Files
import java.nio.file.Path

fun Path.exists(): Boolean = Files.exists(this)