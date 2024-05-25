package xyz.mriganka.elemental

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.ashley.entity
import ktx.ashley.tagFor
import ktx.ashley.with
import ktx.math.plusAssign
import ktx.math.times

class Main : KtxGame<KtxScreen>() {
    private val manager = AssetManager()
    private val resources = Resources(manager)

    override fun create() {
        info(Gdx.graphics.glVersion.vendorString)
        resources.finishLoading()

        addScreen(FirstScreen(resources))
        setScreen<FirstScreen>()
    }

    override fun dispose() {
        resources.unloadAll()
    }
}

class PlayerC() : Component
var Entity.isPlayer by tagFor<PlayerC>()

class FirstScreen(val resources: Resources) : KtxScreen {
    private val player: Entity
    private val batch = SpriteBatch()
    private val viewport = FitViewport(WIDTH, HEIGHT)
    private val engine = PooledEngine()

    init {
        engine.add {
            player = entity {
                with<PlayerC> { }
                with<TextureC> { texture = resources.character }
                with<TransformC> { }
            }
        }

        engine.addSystem(VisualSystem(batch, viewport.camera))
        engine.addSystem(InputSystem(player))
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.LIGHT_GRAY)
        engine.update(delta)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}

class InputSystem(val player: Entity): EntitySystem() {
    val dq = Vector2()

    init {
        priority = INPUT_PRIORITY
    }

    override fun update(deltaTime: Float) {
        dq.setZero()

        if (Gdx.input.isKeyPressed(Keys.A)) {
            dq.x -= 1
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            dq.x += 1
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            dq.y -= 1
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            dq.y += 1
        }

        player.transform.translation += dq * deltaTime * SPEED
    }
}