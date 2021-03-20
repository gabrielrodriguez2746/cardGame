plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "$rootDir/buildSrc/android-base.kts")

android {

    defaultConfig { applicationId = "com.codechallenge.cardgame" }
}

dependencies {

    implementation(Libraries.Kotlin.stdLib)
    implementation(project(":modules:design"))
    implementation(project(":modules:core:navigation"))

    implementation(project(":features:landing"))

    implementation(Libraries.Externals.dagger)
    kapt(Libraries.Externals.daggerCompiler)
}
