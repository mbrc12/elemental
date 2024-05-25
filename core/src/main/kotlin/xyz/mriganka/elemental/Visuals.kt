package xyz.mriganka.elemental

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import ktx.ashley.allOf
import ktx.ashley.propertyFor
import ktx.graphics.use

class TextureC() : Component {
    var texture: Texture? = null
}

var Entity.texture by propertyFor<TextureC>()

class TransformC() : Component {
    var translation: Vector2 = Vector2.Zero
    var rotation: Float = 0f
    var flipX: Boolean = false
    var flipY: Boolean = false
}

var Entity.transform by propertyFor<TransformC>()

class VisualSystem(val batch: SpriteBatch, val camera: Camera) :
    IteratingSystem(allOf(TextureC::class, TransformC::class).get()) {

    init {
        priority = VISUALS_PRIORITY
    }

    override fun processEntity(id: Entity, delta: Float) {
        batch.use(camera) {
            val texture = id.texture.texture ?: return
            val transform = id.transform

            it.draw(
                texture,
                transform.translation.x,
                transform.translation.y,
                0f,
                0f,
                texture.width * 1f,
                texture.height * 1f,
                1f,
                1f,
                transform.rotation,
                0,
                0,
                texture.width,
                texture.height,
                transform.flipX,
                transform.flipY
            )
        }
    }
}
