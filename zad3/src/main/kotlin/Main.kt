import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent

fun main() {
    startKtorServer()
    Database.init()

    val token = "INSERT_TOKEN"

    val jda = JDABuilder.createDefault(token)
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .setActivity(Activity.playing("with Ktor"))
        .addEventListeners(CommandListener())
        .build()

    jda.updateCommands().addCommands(
        Commands.slash("hello", "Say hello to the bot"),
        Commands.slash("categories", "Show list of categories"),
        Commands.slash("products", "Show products list").addOption(OptionType.STRING, "category", "Filter by category name", false),
    ).queue()

    jda.awaitReady()
    println("Bot is ready and commands are registered!")
}

class CommandListener : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        when (event.name) {
            "hello" -> CommandActions.handleHello(event)
            "categories" -> CommandActions.handleCategories(event)
            "products" -> CommandActions.handleProducts(event)
        }
    }
}

fun startKtorServer() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Discord Bot is running!")
            }
            get("/status") {
                call.respondText("Bot is online!")
            }
        }
    }.start(wait = false)
}
