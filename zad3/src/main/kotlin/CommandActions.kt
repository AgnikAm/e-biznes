import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.lowerCase


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

        event.reply("**Available categories:**\n• ${categories.joinToString("\n• ")}")
            .setEphemeral(true)
            .queue()
    }

    fun handleProducts(event: SlashCommandInteractionEvent) {
        event.deferReply().queue()

        val categoryName = event.getOption("category")?.asString?.trim()

        val products = transaction {
            (Products innerJoin Categories)
                .selectAll()
                .apply {
                    if (!categoryName.isNullOrEmpty()) {
                        andWhere { Categories.name.lowerCase() eq categoryName }
                    }
                }
                .map {
                    "• ${it[Products.name]} (${it[Categories.name]})"
                }
        }

        val response = if (products.isEmpty()) {
            "No products found in database"
        } else {
            "**Products:**\n${products.joinToString("\n")}"
        }

        event.hook.editOriginal(response).queue()
    }
}