package io.github.ignitepoland.tickets.commands

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class PingCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val commandName = event.name
        if (commandName != "ping") {
            return
        }
        event.reply("Pong!").queue()
    }
}