package xyz.mriganka.elemental

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.ashley.add
import ktx.ashley.entity
import ktx.ashley.tagFor
import ktx.ashley.with
import ktx.box2d.createWorld

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
    private val batch = SpriteBatch()
    private val viewport = FitViewport(WIDTH, HEIGHT)

    private val engine = PooledEngine()
    private val player: Entity

    private val world = createWorld(gravity = Vector2(0f, -GRAVITY))
    private val b2dr = Box2DDebugRenderer()

    init {
        engine.add {
            player = entity {
                with<PlayerC> { }
                with<KinematicsC> { setupPlayerBody(world) }
                with<SpriteC> {
                    sprite = Sprite(resources.character)
                    sprite.setOriginCenter()
                }
            }
        }

        engine.addSystem(VisualSystem(batch, viewport.camera))
        engine.addSystem(MovementSystem(player))
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(Color.LIGHT_GRAY)

        engine.update(delta)

        b2dr.render(world, viewport.camera.combined)

        world.step(delta, 6,2)

//        info(Gdx.graphics.framesPerSecond)

//        info(player.kinematics.body.linearVelocity)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }
}
