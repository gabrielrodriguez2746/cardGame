plugins {
    id("com.android.library")
    kotlin("android")
}

apply(from = "$rootDir/buildSrc/android-base.kts")

dependencies {

    implementation(Libraries.Kotlin.stdLib)
    api(Libraries.Android.material)

}