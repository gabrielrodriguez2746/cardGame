plugins {
    id("com.android.library")
    kotlin("android")
    id("de.mannodermaus.android-junit5")
}

apply(from = "$rootDir/buildSrc/android-base.kts")

dependencies {

    implementation(Libraries.Kotlin.stdLib)

    testImplementation(Libraries.Test.mockk)
    testImplementation(Libraries.Test.junit5Api)
    testRuntimeOnly(Libraries.Test.junit5Engine)

}