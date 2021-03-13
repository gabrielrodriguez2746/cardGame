const val kotlin = "1.4.30"

object Build {

    private object Versions {
        const val buildTools = "4.0.0"
    }

    const val androidGradle = "com.android.tools.build:gradle:${Versions.buildTools}"
    const val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"

}

object AndroidSdk {
    const val min = 21
    const val compile = 30
    const val target = compile
}

object Plugins {
    object Versions {
        const val ktlint = "9.4.1"
    }

    const val ktlintName = "org.jlleitschuh.gradle.ktlint"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
}

object Libraries {

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
    }

    object Android {
        private object Versions {
            const val material = "1.3.0"
        }

        const val material = "com.google.android.material:material:${Versions.material}"
    }

}

