package com.polymorph.game

import com.badlogic.gdx.ApplicationListener
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

object PolymorphGame : ApplicationListener {

    val GAME_NAME: String = "Project Polymorph"
    val FIXED_TIME_STEP: Float = 1f / 60f
    val MAX_DELTA_TIME: Float = 1f

    private val camera: OrthographicCamera by lazy { OrthographicCamera() }
    private val viewport: Viewport by lazy { ScreenViewport(this.camera) }
    private val batch: SpriteBatch by lazy { SpriteBatch() }
    private val img: Texture by lazy { Texture(FileHandle(FileLocations.dataDirectory.resolve("blue_bandito_256.png").toFile())) }
    private val sprite: Sprite by lazy { Sprite(img) }

    private var timeSinceLastFixedFrameUpdate: Float = 0f

    val assets: PolymorphAssetManager = PolymorphAssetManager()
    var arguments: ProgramArguments = ProgramArguments()

    override fun create() {
        viewport.apply()
        camera.position.set(0f, 0f, 0f)

        sprite.setPosition(0f, 0f)
    }

    override fun render() {
        timeSinceLastFixedFrameUpdate = Math.min(timeSinceLastFixedFrameUpdate + Gdx.graphics.rawDeltaTime, MAX_DELTA_TIME)
        while (timeSinceLastFixedFrameUpdate >= FIXED_TIME_STEP) {
            timeSinceLastFixedFrameUpdate -= FIXED_TIME_STEP
            doFixedFrameUpdate(FIXED_TIME_STEP)
        }

        camera.update()

        clearScreen(Color.BLACK)

        batch.projectionMatrix = camera.combined
        batch.begin()
        sprite.draw(batch)
        batch.end()
    }

    fun doFixedFrameUpdate(delta: Float) {
        EntityEngine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        this.viewport.update(width, height)
        this.camera.update()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun dispose() {
        batch.dispose()
        img.dispose()
    }

    fun exit() {
        Gdx.app.exit()
    }
}
