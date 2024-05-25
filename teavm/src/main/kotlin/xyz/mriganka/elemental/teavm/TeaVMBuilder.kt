package xyz.mriganka.elemental.teavm

import java.io.File
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuildConfiguration
import com.github.xpenatan.gdx.backends.teavm.config.TeaBuilder
import com.github.xpenatan.gdx.backends.teavm.config.plugins.TeaReflectionSupplier
import com.github.xpenatan.gdx.backends.teavm.gen.SkipClass
import xyz.mriganka.elemental.*

/** Builds the TeaVM/HTML application. */
@SkipClass
object TeaVMBuilder {
    @JvmStatic fun main(arguments: Array<String>) {
        val teaBuildConfiguration = TeaBuildConfiguration().apply {
            assetsPath.add(File("../assets"))
            webappPath = File("build/dist").canonicalPath
            // Register any extra classpath assets here:
            // additionalAssetsClasspathFiles += "xyz/mriganka/elemental/asset.extension"
        }

        // Register any classes or packages that require reflection here:

        for (clazz in reflectionClasses) {
            TeaReflectionSupplier.addReflectionClass(clazz.java)
        }

        val tool = TeaBuilder.config(teaBuildConfiguration)
        tool.mainClass = "xyz.mriganka.elemental.teavm.TeaVMLauncher"
        TeaBuilder.build(tool)
    }
}
