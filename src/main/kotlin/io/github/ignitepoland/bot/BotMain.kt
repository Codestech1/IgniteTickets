package io.github.ignitepoland.bot

import dev.minn.jda.ktx.interactions.commands.restrict
import dev.minn.jda.ktx.interactions.commands.slash
import net.dv8tion.jda.api.JDABuilder
import dev.minn.jda.ktx.interactions.commands.updateCommands
import io.github.ignitepoland.bot.module.Ticket
import net.dv8tion.jda.api.Permission

fun main() {
    val jda = JDABuilder.createLight(System.getProperty("Token"))
        .addEventListeners(Ticket())
        .build()
        .awaitReady()

    jda.updateCommands {
        slash("setup", "🛠️ Służy do ustawienia bota") {
            restrict(true, Permission.ADMINISTRATOR)
        }
    }.queue()
}