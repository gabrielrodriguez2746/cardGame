plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "$rootDir/buildSrc/android-base.kts")

android {
    buildFeatures.viewBinding = true
}

dependencies {

    implementation(Libraries.Kotlin.stdLib)
    implementation(project(":modules:design"))

    implementation(Libraries.Android.appcompat)

}