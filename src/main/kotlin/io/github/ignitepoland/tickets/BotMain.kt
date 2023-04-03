package io.github.ignitepoland.tickets

import net.dv8tion.jda.api.JDABuilder

fun main() {
    val jda = JDABuilder.createLight(System.getProperty("Token"))
        .build()
        .awaitReady()
}