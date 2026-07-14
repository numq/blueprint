plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvm()

    iosX64()

    iosArm64()

    iosSimulatorArm64()

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class) wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.serialization.core)
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}