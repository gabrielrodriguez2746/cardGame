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
        const val junit5 = "1.7.1.1"
    }

    const val ktlintName = "org.jlleitschuh.gradle.ktlint"
    const val ktlint = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.ktlint}"
    const val junit5 = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5}"
    const val junit5Name = "de.mannodermaus.android-junit5"
}

object Libraries {

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
    }

    object Android {
        private object Versions {
            const val material = "1.3.0"
            const val appcompat = "1.2.0"
            const val fragments = "1.3.1"
        }

        const val material = "com.google.android.material:material:${Versions.material}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
        const val fragments = "androidx.fragment:fragment-ktx:${Versions.fragments}"
    }

    object Externals {

        private object Versions {
            const val dagger = "2.25.3"
        }

        const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    object Test {

        private object Version {
            const val junit5 = "5.7.1"
            const val mockk = "1.11.0"
        }

        const val mockk = "io.mockk:mockk:${Version.mockk}"
        const val junit5Api = "org.junit.jupiter:junit-jupiter-api:${Version.junit5}"
        const val junit5Engine = "org.junit.jupiter:junit-jupiter-engine:${Version.junit5}"

    }

}

