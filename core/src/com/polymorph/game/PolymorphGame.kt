package com.polymorph.game

import com.badlogic.gdx.Files
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.polymorph.game.entity.EntityEngine
import com.polymorph.game.io.FileLocations
import com.polymorph.game.io.PolymorphAssetManager
import com.polymorph.game.util.clearScreen
import ktx.app.KotlinApplication

object PolymorphGame : KotlinApplication() {

    val GAME_NAME: String = "Project Polymorph"

    val assets: PolymorphAssetManager = PolymorphAssetManager()

    private val camera: OrthographicCamera by lazy { OrthographicCamera() }
    private val viewport: Viewport by lazy { ScreenViewport(this.camera) }
    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val img: Texture by lazy { Texture(FileHandle(FileLocations.DataDirectory.resolve("blue_bandito_256.png").toFile())) }
    private val sprite: Sprite by lazy { Sprite(img) }

    override fun create() {
        viewport.apply()
        camera.position.set(0f, 0f, 0f)

        sprite.setPosition(0f, 0f)
    }

    override fun render(delta: Float) {
        camera.update()
        EntityEngine.update(delta)

        clearScreen(Color.BLACK)

        batch.projectionMatrix = camera.combined
        batch.begin()
        sprite.draw(batch)
        batch.end()
    }

    override fun resize(width: Int, height: Int) {
        this.viewport.update(width, height)
        this.camera.update()
    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }

    fun exit() {
        Gdx.app.exit()
    }
}
