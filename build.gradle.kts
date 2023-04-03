plugins {
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "io.github.ignitepoland.administration"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.6")
    implementation("com.github.MinnDevelopment:jda-ktx:0.10.0-beta.1")
    implementation("ch.qos.logback:logback-classic:1.4.6")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.github.ignitepoland.administration.BotMainKt"
    }
}