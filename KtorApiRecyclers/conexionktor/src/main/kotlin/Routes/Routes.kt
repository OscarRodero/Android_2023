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
            var usu = call.receive<User>()
            var log = StaticConnection.login(usu)
            if(log==null){
                call.response.status(HttpStatusCode.NotFound)
                return@post call.respond("Error >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>")
            }
        }
    }
}