buildscript {

    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath(Build.androidGradle)
        classpath(Build.kotlinGradle)
        classpath(Plugins.ktlint)
        classpath(Plugins.junit5)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

plugins {
    id(Plugins.ktlintName) version Plugins.Versions.ktlint
}
