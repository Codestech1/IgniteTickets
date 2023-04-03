package io.github.ignitepoland.administration.module

import dev.minn.jda.ktx.generics.getChannel
import dev.minn.jda.ktx.messages.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.buttons.Button
import java.awt.Color

class Ticket : ListenerAdapter() {

    private val channelId = System.getProperty("TicketChannel")
    private val categoryId = System.getProperty("TicketCategory")
    private val administrationRoleId = System.getProperty("AdministrationRole")

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val commandName = event.name
        val guild = event.guild
        if (commandName != "setup") {
            return
        }

        val embed = EmbedBuilder()
        embed.title = "**Tickety**"
        embed.color = Color(130, 0, 255).rgb
        embed.description = "Kliknij na przycisk poniżej, aby otworzyć ticket.\n" +
                "Ticket służy do zapytań do administracji.\n" +
                "Otwieranie ich **bez powodu** może skutkować banem."

        val button = Button.danger("ticket", "Otwórz Ticket")
            .withEmoji(Emoji.fromUnicode("🎫"))

        val channel = guild?.getChannel<TextChannel>(channelId)
        channel?.sendMessageEmbeds(embed.build())?.addActionRow(button)?.queue()
    }

    override fun onButtonInteraction(event: ButtonInteractionEvent) {
        val button = event.button
        when (button.id) {
            "ticket" -> onTicketOpen(event)
            "ticketClose" -> onTicketClose(event)
        }
    }

    private fun onTicketOpen(event: ButtonInteractionEvent) {
        val user = event.user
        val guild = event.guild

        val category = guild?.getCategoryById(categoryId)
        if (category != null) {
            category.createTextChannel("ticket-${user.name}").queue {
                val manager = it.manager

                manager.putRolePermissionOverride(
                    guild.publicRole.idLong,
                    null,
                    listOf(Permission.VIEW_CHANNEL)
                )

                manager.putMemberPermissionOverride(
                    user.idLong,
                    listOf(
                        Permission.VIEW_CHANNEL,
                        Permission.MESSAGE_SEND
                    ),
                    null
                ).queue()

                val embed = EmbedBuilder()
                embed.title = "**Ticket**"
                embed.description = "Ticket został stworzony przez: ${user.asMention}.\n" +
                        "Prosimy nie oznaczać administracji. Po prostu proszę poczekać."
                embed.color = Color.MAGENTA.rgb

                val adminRole = guild.getRoleById(administrationRoleId)

                it.sendMessageEmbeds(embed.build())
                    .addContent(user.asMention)
                    .addContent(" ${adminRole!!.asMention}")
                    .addActionRow(
                        Button.danger("ticketClose", "Zamknij Ticket")
                            .withEmoji(Emoji.fromUnicode("🛑"))
                    )
                    .queue()
            }

            event.reply("**Operacja przebiegła pomyślnie**")
                .setEphemeral(true)
                .queue()
        }
    }

    private fun onTicketClose(event: ButtonInteractionEvent) {
        val member = event.member
        val channel = event.channel.asGuildMessageChannel()

        if (member == null) {
            return
        }

        if (!member.hasPermission(channel, Permission.MANAGE_THREADS)) {
            event.reply("**Nie posiadasz wystarczających permisji, aby to zrobić**")
            return
        }

        channel.delete().queue()

        event.reply("**Operacja przebiegła pomyślnie**")
            .setEphemeral(true)
            .queue()
    }
}
