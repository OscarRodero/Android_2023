package API

import Modelo.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
    @GET("pokedex/national")
    fun getPokemones():Call<MutableList<Pokemon>>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>

}