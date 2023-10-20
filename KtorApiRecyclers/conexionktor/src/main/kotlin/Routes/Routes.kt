package Routes

import Models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dbRouting()
{
    route("/users")
    {
        get {
            var users = StaticConnection.obtenerTodosLosUsuarios()
            if(users.isNotEmpty()) {
                call.respond(users)
                println("=============================")
                println("Envié la lista ${users}")
            }else{
                call.respondText("No hay usuarios",status = HttpStatusCode.OK)
            }
        }

    }
}