package xyz.mriganka.elemental

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import kotlin.math.roundToInt

fun info(vararg objs: Any) {
    val data = StringBuilder()
    for (obj in objs) {
        data.append(" ")
        data.append(obj)
    }

    Gdx.app.log("INFO", data.toString())
}

fun Vector2.round() {
    x = x.roundToInt().toFloat()
    y = y.roundToInt().toFloat()
}
