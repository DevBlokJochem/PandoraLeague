import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.yaml.snakeyaml.Yaml

buildscript {
    repositories {
        jcenter() // also works with mavenCentral()
        mavenCentral()
    }
    dependencies {
        classpath("org.yaml", "snakeyaml", "2.2")
    }
}

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("jvm") version "1.9.10"

    `maven-publish`
}
val cfg: Map<String, Any> = Yaml().load(File("src/main/resources/plugin.yml").inputStream())

group = "nl.jochem.pandoraleague"
version = cfg["version"] ?: ""
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    jcenter()
    mavenCentral()
    maven(url ="https://repo.jorisg.com/releases")
    maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    maven(url = "https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("com.gufli.kingdomcraft.starter:api:6.12.3")
    compileOnly("me.clip:placeholderapi:2.10.9")

    implementation("com.github.hazae41:mc-kutils:master-SNAPSHOT")
}

tasks.named<ShadowJar>("shadowJar") {
    manifest {
        attributes(mapOf("Main-Class" to "$group/Main"))
    }
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks {
    shadowJar {
//        archiveFileName.set(rootProject.name + ".jar")
        relocate("com.google.gson", "shadow.com.google.gson")
    }
}