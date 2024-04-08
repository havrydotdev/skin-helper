import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.23")
    }
}

plugins {
    id("java-library")
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("java")
    kotlin("jvm") version "1.6.10"
}

apply(plugin = "java")
apply(plugin = "java-library")
apply(plugin = "com.github.johnrengelman.shadow")
apply(plugin = "kotlin")

group = "org.havry"
val pluginName: String by project
val pluginVersion: String by project
val minecraftVersion: String by project

val withDrivers: Configuration by configurations.creating {
    extendsFrom(configurations.compileOnly.get(), configurations.implementation.get())
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    processResources {
        outputs.upToDateWhen { false }

        filesMatching("**plugin.yml") {
            expand(
                mutableMapOf(
                    Pair("pluginVersion", pluginVersion),
                    Pair("minecraftVersion", minecraftVersion)
                )
            )
        }

    }

    shadowJar {
        archiveFileName.set("${pluginName}-${pluginVersion}.jar")
    }

    register<ShadowJar>("shadowJarDrivers") {
        from(sourceSets.main.get().output)
        archiveFileName.set("${pluginName}-${pluginVersion}-drv.jar")
        configurations = listOf(withDrivers)
    }

    register<Exec>("createGitHubRelease") {
        dependsOn(shadowJar)
        dependsOn("shadowJarDrivers")

        commandLine("git", "tag", "-a", "v${pluginVersion}", "-m", "Release v${pluginVersion}")
        commandLine("git", "push", "--tags")

        commandLine("gh", "release", "create", "v${pluginVersion}", "-F", "changelog.md",
            "-t", "v${pluginVersion}",
            "build/libs/${pluginName}-${pluginVersion}.jar", "build/libs/${pluginName}-${pluginVersion}-drv.jar")
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven { setUrl("https://jitpack.io") }
    maven(url = "https://repo.inventivetalent.org/repository/public")
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.12.2-R0.1-SNAPSHOT") {
        isTransitive = false
    }
    implementation("com.github.gavrylenkoIvan:block-client:1.0.4")
    implementation("org.mineskin:java-client:1.2.4-SNAPSHOT")
    implementation(kotlin("stdlib-jdk8"))
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "17"
}