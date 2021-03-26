plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id(Plugins.junit5Name)
}

apply(from = "$rootDir/buildSrc/android-base.kts")

android {
    buildFeatures.viewBinding = true
}

dependencies {

    implementation(Libraries.Kotlin.stdLib)

    implementation(Libraries.Android.viewModel)
    implementation(Libraries.Android.liveData)

    implementation(Libraries.Externals.dagger)
    kapt(Libraries.Externals.daggerCompiler)

    testImplementation(Libraries.Test.mockk)
    testImplementation(Libraries.Test.junit5Api)
    testImplementation(Libraries.Test.kotestCore)
    testImplementation(Libraries.Android.archTesting)
    testRuntimeOnly(Libraries.Test.junit5Engine)
}
