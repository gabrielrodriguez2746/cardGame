plugins {
    id("com.android.library")
    kotlin("android")
    id(Plugins.junit5Name)
}

apply(from = "$rootDir/buildSrc/android-base.kts")

dependencies {

    implementation(Libraries.Kotlin.stdLib)
    implementation(Libraries.Android.fragments)

    testImplementation(Libraries.Test.mockk)
    testImplementation(Libraries.Test.junit5Api)
    testRuntimeOnly(Libraries.Test.junit5Engine)

}