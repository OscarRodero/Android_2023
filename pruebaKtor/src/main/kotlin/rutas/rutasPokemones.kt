package rutas

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Respuesta
import modelo.Pokemon

private val pokemons = arrayListOf<Pokemon>(
    Pokemon("001", "Bulbasaur", "Planta") ,
    Pokemon("004", "Charmander", "Fuego"),
    Pokemon("007", "Squirtle", "Agua")
)

fun Route.pokeRouting(){
    route("/pokemons"){
        get {
            if(pokemons.isNotEmpty()){
                call.response.status(HttpStatusCode.OK)
                call.respond(pokemons)
            }else{
                call.respondText("No hay usuarios disponibles", status = HttpStatusCode.OK)
            }
        }
        get("{id}"){
            val id = call.parameters["id"]
            if(id == null){
                return@get call.respondText("id vacío en la url", status = HttpStatusCode.BadRequest)
            }else{
                val pokemon = pokemons.find{it.id == id}
                if(pokemon!=null){
                    call.response.status(HttpStatusCode.OK)
                    call.respond(pokemon)
                }else{
                    call.response.status(HttpStatusCode.NotFound)
                    return@get call.respond(Respuesta("Pokemon ${id} no encontrado", HttpStatusCode.NotFound.value))
                }
            }
        }
        post{
            val pk = call.receive<Pokemon>()
            val pokemon = pokemons.find { it.id == pk.id }
            if(pokemon!=null){
                call.response.status(HttpStatusCode.Conflict)
                return@post call.respond(Respuesta("Pokemon ${pk.id} repetido", HttpStatusCode.Conflict.value))
            }else{
                pokemons.add(pk)
                call.respondText("Pokemon creado",status = HttpStatusCode.Created)
                return@post call.respond(Respuesta("Pokemon ${pk.id} creado", HttpStatusCode.Accepted.value))
            }
        }
        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respondText("id vacío en la url", status = HttpStatusCode.BadRequest)
            val pk = call.receive<Pokemon>()
            val pos = pokemons.indexOfFirst{ it.id == id}
            if (pos == -1){
                call.respondText("No encontrado",status = HttpStatusCode.NotFound)
            }
            else {
                pokemons[pos] = pk
                call.respondText("Pokemon mutado",status = HttpStatusCode.Accepted)
            }
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("id vacío en la url", status = HttpStatusCode.BadRequest)
            if (pokemons.removeIf { it.id == id }){
                call.respondText("Pokemon suprimido",status = HttpStatusCode.Accepted)
            }
            else {
                call.respondText("Pokemon no encontrado",status = HttpStatusCode.NotFound)
            }
        }
    }


}