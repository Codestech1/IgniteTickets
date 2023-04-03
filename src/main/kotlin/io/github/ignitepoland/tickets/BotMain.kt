package io.github.ignitepoland.tickets

import dev.minn.jda.ktx.interactions.commands.option
import dev.minn.jda.ktx.interactions.commands.restrict
import dev.minn.jda.ktx.interactions.commands.slash
import net.dv8tion.jda.api.JDABuilder
import dev.minn.jda.ktx.interactions.commands.updateCommands
import io.github.ignitepoland.tickets.commands.PingCommand
import net.dv8tion.jda.api.Permission

fun main() {
    val jda = JDABuilder.createLight(System.getProperty("Token"))
        .addEventListeners(PingCommand())
        .build()
        .awaitReady()

    jda.updateCommands {
        slash("ping", "A simple ping-pong command") {
            restrict(true, Permission.MESSAGE_SEND)
            option<String>("argument", "An example argument")
        }
    }.queue()
}