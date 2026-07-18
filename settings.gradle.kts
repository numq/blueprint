rootProject.name = "blueprint"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":example:client")
project(":example:client").projectDir = file("example/client")

include(":example:server")
project(":example:server").projectDir = file("example/server")

include(":library:chain")
project(":library:chain").projectDir = file("library/chain")

include(":library:dsl")
project(":library:dsl").projectDir = file("library/dsl")

include(":library:protocol")
project(":library:protocol").projectDir = file("library/protocol")

include(":library:renderer")
project(":library:renderer").projectDir = file("library/renderer")

include(":library:renderer-compose")
project(":library:renderer-compose").projectDir = file("library/renderer-compose")

include(":library:runtime")
project(":library:runtime").projectDir = file("library/runtime")