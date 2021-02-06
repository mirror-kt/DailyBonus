import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "dev.mirror-kt"
version = "0.2.0"

repositories {
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://repo.codemc.org/repository/maven-public")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    implementation("de.tr7zw:item-nbt-api:2.7.1")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
        targetCompatibility = "11"
    }

    getByName<ProcessResources>("processResources") {
        filter<org.apache.tools.ant.filters.ReplaceTokens>(
            "tokens" to mapOf(
                "VERSION" to version
            )
        )
    }

    getByName<ShadowJar>("shadowJar") {
        relocate("de.tr7zw.changeme.nbtapi", "dev.mirror.kt.minecraft.nbtapi")
    }
}
