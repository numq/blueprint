import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(projects.example.client.shared)
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "io.github.numq.blueprint.example.client.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Deb)
            packageName = "io.github.numq.blueprint.example.client"
            packageVersion = "1.0.0"
        }
    }
}