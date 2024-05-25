package xyz.mriganka.elemental

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import ktx.assets.AssetGroup
import ktx.assets.getValue
import kotlin.reflect.KClass

const val WIDTH = 400f
const val HEIGHT = 300f

const val SPEED = 100f

const val VISUALS_PRIORITY = 100
const val INPUT_PRIORITY = 1

val info = { obj: Any -> Gdx.app.log("INFO", obj.toString()) }

class Resources(manager: AssetManager) : AssetGroup(manager, filePrefix = "kenney/Tiles/") {
    val character by asset<Texture>("Characters/tile_0002.png")
}

val reflectionClasses: ArrayList<KClass<out Component>> = arrayListOf(
    PlayerC::class,
    TextureC::class,
    TransformC::class
)