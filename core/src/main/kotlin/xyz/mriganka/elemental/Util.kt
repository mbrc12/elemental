package xyz.mriganka.elemental

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector2
import java.lang.StringBuilder
import kotlin.math.roundToInt

private val data = StringBuilder()

fun info(vararg objs: Any) {
    data.clear()

    for (obj in objs) {
        data.append(obj)
    }

    Gdx.app.log("INFO", data.toString())
}

fun Vector2.round() {
    x = x.roundToInt().toFloat()
    y = y.roundToInt().toFloat()
}
