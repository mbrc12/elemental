package xyz.mriganka.elemental

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import ktx.ashley.allOf
import ktx.ashley.propertyFor

class SpriteC() : Component {
    lateinit var sprite: Sprite
}

var Entity.sprite by propertyFor<SpriteC>()

class VisualSystem(val batch: SpriteBatch, val camera: Camera) :
    IteratingSystem(allOf(SpriteC::class).get()) {

    init {
        priority = VISUALS_PRIORITY
    }

    override fun startProcessing() {
        batch.projectionMatrix = camera.combined
        batch.begin()
    }

    override fun processEntity(id: Entity, delta: Float) {
        id.sprite.sprite.draw(batch)
    }

    override fun endProcessing() {
        batch.end()
    }
}
