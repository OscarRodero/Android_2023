package Routes

import Models.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.dbRouting(){
    route("/users"){
        get {
            var users = StaticConnection.obtainAllUsers()
            if(users.isNotEmpty()) {
                call.respond(users)
            }else{
                call.respondText("No hay usuarios",status = HttpStatusCode.OK)
            }
        }
    }
    route("/login"){
        post(){
            val user = call.receive<AuxUser>()
            val log = StaticConnection.login(user)
            if (log == null) {
                call.respondText("Error: La autenticación falló", status = HttpStatusCode.NotFound)
            } else {
                // Aquí puedes responder con un token de acceso u otra confirmación
                call.respond(log)
            }
        }
    }
}