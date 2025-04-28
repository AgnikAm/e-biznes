import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object Categories : IntIdTable("categories") {
    val name = varchar("name", 50)
}

object Products : IntIdTable("products") {
    val name = varchar("name", 100)
    val categoryId = reference("category_id", Categories)
}

object Database {
    fun init() {
        try {
            // Creates database file in project directory
            val dbFile = File("discord_bot.db").apply {
                parentFile?.mkdirs() // Ensure directory exists
            }

            Database.connect(
                url = "jdbc:sqlite:${dbFile.absolutePath}",
                driver = "org.sqlite.JDBC"
            )

            transaction {
                SchemaUtils.createMissingTablesAndColumns(Categories, Products)

                if (Categories.selectAll().empty()) {
                    val fantasyId = Categories.insert {
                        it[name] = "Fantasy"
                    } get Categories.id

                    val scifiId = Categories.insert {
                        it[name] = "Science Fiction"
                    } get Categories.id

                    val adventureId = Categories.insert {
                        it[name] = "Adventure"
                    } get Categories.id

                    val horrorId = Categories.insert {
                        it[name] = "Horror"
                    } get Categories.id

                    val literaryId = Categories.insert {
                        it[name] = "Literary Fiction"
                    } get Categories.id

                    Products.insert {
                        it[name] = "The Lord of the Rings"
                        it[categoryId] = fantasyId
                    }

                    Products.insert {
                        it[name] = "Solaris"
                        it[categoryId] = scifiId
                    }

                    Products.insert {
                        it[name] = "Treasure Island"
                        it[categoryId] = adventureId
                    }

                    Products.insert {
                        it[name] = "Salem’s Lot"
                        it[categoryId] = horrorId
                    }

                    Products.insert {
                        it[name] = "Sputnik Sweetheart"
                        it[categoryId] = literaryId
                    }
                }
            }
            println("SQLite database initialized at: ${dbFile.absolutePath}")
        } catch (e: Exception) {
            System.err.println("Database initialization failed:")
            e.printStackTrace()
        }
    }
}