plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.maven.publish)
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

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(
        groupId = "io.github.numq.blueprint", artifactId = "runtime", version = project.version.toString()
    )

    pom {
        name.set("Blueprint Runtime")
        description.set("Core models, serialization setup, and state resolution logic for the Blueprint Server-Driven UI framework.")
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