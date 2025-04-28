import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object CommandActions {
    // Hello command action
    fun handleHello(event: SlashCommandInteractionEvent) {
        event.reply("Hi! I'm KtorBot").queue()
    }

    // Categories command action
    fun handleCategories(event: SlashCommandInteractionEvent) {
        val categories = transaction {
            Categories.selectAll().map { it[Categories.name] }
        }

        event.reply("**Available categories:**\n${categories.joinToString("\n")}")
            .setEphemeral(true)
            .queue()
    }
}