package com.polymorph.game.io

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader

class PolymorphAssetManager: AssetManager() {
    init {
        setLoader(TiledMap::class.java, TmxMapLoader())
    }
}