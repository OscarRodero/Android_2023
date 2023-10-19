package rutas

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Respuesta
import modelo.Usuario

private val usuarios = arrayListOf<Usuario>(
    Usuario("1A", "DAM2", "1234","555 454554") ,
    Usuario("2B", "DAW2", "1234","555 897999")
)

fun Route.userRouting(){  //Esta ruta se incluirá en el archivo Routing.
        route("/listado") {
            get {
                if (usuarios.isNotEmpty()) {
                    call.respond(usuarios)
                } else {
                    call.respondText("No hay usuarios", status = HttpStatusCode.OK)
                }
            }
            get("{dni?}") {
                val dni = call.parameters["dni"] ?: return@get call.respondText(
                    "id vacío en la url",
                    status = HttpStatusCode.BadRequest
                )

                try {
                    val pruebaParam = dni.toString()
                } catch (e: Exception) {
                    call.response.status(HttpStatusCode.BadRequest)
                    return@get call.respond(Respuesta("Parámetro ${dni} no válido", HttpStatusCode.BadRequest.value))
                }
                val usuario = usuarios.find { it.dni == dni }
                if (usuario == null) {
                    call.response.status(HttpStatusCode.NotFound)
                    return@get call.respond(Respuesta("Usuario ${dni} no encontrado", HttpStatusCode.NotFound.value))
                    //call.respondText("Usuario ${id} no encontrado", status = HttpStatusCode.NotFound)
                }
                call.respond(usuario)
            }
        }
        route("/login") {
            post{
                val us = call.receive<Usuario>()
                val usuario = usuarios.find { it.dni == us.dni && it.clave == us.clave }
                if (usuario == null) {
                    call.response.status(HttpStatusCode.NotFound)
                    return@post call.respond(Respuesta("Usuario ${us.dni} login incorrecto", HttpStatusCode.NotFound.value))
                    //call.respondText("Usuario ${id} no encontrado", status = HttpStatusCode.NotFound)
                }
                call.respond(usuario)
            }
        }
        route("/registrar") {
            post{
                val us = call.receive<Usuario>()
                usuarios.add(us)
                call.respondText("Usuario creado",status = HttpStatusCode.Created)
            }
        }
        route("/borrar") {
            delete("{dni?}") {
                val dni = call.parameters["dni"] ?: return@delete call.respondText("id vacío en la url", status = HttpStatusCode.BadRequest)
                if (usuarios.removeIf { it.dni == dni }){
                    call.respondText("Usuario eliminado",status = HttpStatusCode.Accepted)
                }
                else {
                    call.respondText("No encontrado",status = HttpStatusCode.NotFound)
                }
            }
        }
        route("/modificar") {
            put("{dni?}") {
                val dni = call.parameters["dni"] ?: return@put call.respondText("id vacío en la url", status = HttpStatusCode.BadRequest)
                val us = call.receive<Usuario>()
                val pos = usuarios.indexOfFirst{ it.dni == dni}
                if (pos == -1){
                    call.respondText("No encontrado",status = HttpStatusCode.NotFound)
                }
                else {
                    usuarios[pos] = us
                    call.respondText("Usuario modificado",status = HttpStatusCode.Accepted)
                }
            }
        }


}