import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080) {
        install(FreeMarker) {
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
        install(Routing) {
            get("/solve") {
                val day = call.parameters["day"]?.toInt() ?: 1
                val month = call.parameters["month"]?.toInt() ?: 1
                val random = call.parameters.contains("random")
                val solution: List<List<Point2D>> = Board(day, month).solve(random)
                    .map { (piece, position) -> piece.blockPostions.map { it + position } }
                call.respond(FreeMarkerContent("solve.ftl", mapOf("solution" to solution)))
            }
        }
    }.start(wait = true)

}