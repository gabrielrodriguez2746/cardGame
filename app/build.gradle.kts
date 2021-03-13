plugins {
    id("com.android.application")
    kotlin("android")
}

apply(from = "$rootDir/buildSrc/android-base.kts")

android {

    defaultConfig { applicationId = "com.codechallenge.cardgame" }
}

dependencies {

    implementation(Libraries.Kotlin.stdLib)
    implementation(Libraries.Android.material)

}
