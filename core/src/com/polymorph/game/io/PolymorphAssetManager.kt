package com.polymorph.game.io

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import org.apache.commons.io.FilenameUtils
import java.nio.file.Path
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass

class PolymorphAssetManager: AssetManager() {

    val TextureExtensions: List<String> = listOf("png")
    val TiledMapExnteions: List<String> = listOf("tmx")

    init {
        setLoader(TiledMap::class.java, TmxMapLoader())
    }

    fun loadInternalFiles() {
        loadResourcesRecursive(FileLocations.DataDirectory)
    }

    fun <T> load(file: Path, type: Class<T>) {
        load(file.toString(), type)
    }

    private fun loadResourcesRecursive(directory: Path) {
        directory.onContents { file ->
            if(file.isDirectory) {
                loadResourcesRecursive(file)
            } else {
                val extension = FilenameUtils.getExtension(file.toString())
                when {
                    TextureExtensions.any { it.equals(extension, true) } -> load(file, Texture::class.java)
                    TiledMapExnteions.any { it.equals(extension, true) } -> load(file, TiledMap::class.java)
                }
            }
        }
    }
}