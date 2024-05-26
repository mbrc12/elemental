package xyz.mriganka.elemental

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.World
import ktx.ashley.propertyFor
import ktx.box2d.body
import ktx.box2d.circle
import ktx.math.plus
import ktx.math.times
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sign

class KinematicsC() : Component {
    lateinit var body: Body

    fun setupPlayerBody(world: World) {
        body = world.body {
            circle(radius = 10f) {
                density = 40f
            }

            type = BodyDef.BodyType.KinematicBody
        }
    }
}

var Entity.kinematics by propertyFor<KinematicsC>()

class MovementSystem(val player: Entity) : EntitySystem() {
    val dq = Vector2()

    init {
        priority = MOVEMENT_PRIORITY
    }

    override fun update(deltaTime: Float) {
        dq.setZero()

        val body = player.kinematics.body
        val velocity = body.linearVelocity
        val speed = velocity.len()
        val accel = 1 - (speed / SPEED).pow(3)

        if (Gdx.input.isKeyPressed(Keys.D)) {
            dq.x += 1
        }
        if (Gdx.input.isKeyPressed(Keys.A)) {
            dq.x -= 1
        }

        if (dq.isZero) {
            dq.setZero()
        }

        if ((dq.x.sign - velocity.x.sign).absoluteValue < 1e-6 && !dq.isZero) {
            body.linearVelocity += dq * accel * deltaTime * 100
        } else {
            body.linearVelocity = dq * BASE_SPEED
        }

        val sprite = player.sprite.sprite
        val position = body.position

        sprite.setOriginBasedPosition(position.x, position.y)

        if (dq.x > 0) {
            sprite.setFlip(true, false)
        }
        if (dq.x < 0) {
            sprite.setFlip(false, false)
        }
    }
}