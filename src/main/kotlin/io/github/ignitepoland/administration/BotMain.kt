package io.github.ignitepoland.administration

import dev.minn.jda.ktx.interactions.commands.option
import dev.minn.jda.ktx.interactions.commands.restrict
import dev.minn.jda.ktx.interactions.commands.slash
import net.dv8tion.jda.api.JDABuilder
import dev.minn.jda.ktx.interactions.commands.updateCommands
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.unions.GuildChannelUnion

fun main() {
    val jda = JDABuilder.createLight(System.getProperty("Token"))
        .build()
        .awaitReady()

    jda.updateCommands {
        slash("setup", "🛠️ Służy do ustawienia bota") {
            restrict(true, Permission.MESSAGE_SEND)
            option<GuildChannelUnion>("kanał", "Kanał do ustawienia bota")
        }
    }.queue()
}