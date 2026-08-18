plugins {
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.wire)
    alias(libs.plugins.maven.publish)
}

kotlin {
    android {
        namespace = "io.github.numq.blueprint.protocol"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        }
    }

    jvm()

    iosX64()

    iosArm64()

    iosSimulatorArm64()

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class) wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.wire.runtime)
        }
    }
}

wire {
    sourcePath {
        srcDir("src/commonMain/proto")
    }

    kotlin {

    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(groupId = "io.github.numq.blueprint", artifactId = "protocol", version = project.version.toString())

    pom {
        name.set("Blueprint Protocol")
        description.set("Protocol Buffers definitions and contracts for the Blueprint Server-Driven UI framework.")
        url.set("https://github.com/numq/blueprint")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("numq")
                name.set("numq")
                url.set("https://github.com/numq")
            }
        }

        scm {
            url.set("https://github.com/numq/blueprint")
            connection.set("scm:git:git://github.com/numq/blueprint.git")
            developerConnection.set("scm:git:ssh://git@github.com/numq/blueprint.git")
        }
    }
}