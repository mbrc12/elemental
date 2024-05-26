@file:JvmName("Lwjgl3Launcher")

package xyz.mriganka.elemental.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import xyz.mriganka.elemental.Main

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
      return

    val displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode()

    Lwjgl3Application(Main(), Lwjgl3ApplicationConfiguration().apply {
//        useVsync(true);
//        setForegroundFPS(displayMode.refreshRate);
//        System.out.println(displayMode)
        setFullscreenMode(displayMode)
        setTitle("Elemental")
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
