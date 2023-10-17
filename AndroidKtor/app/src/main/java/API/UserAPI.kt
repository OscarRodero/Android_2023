package API

import Modelo.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
    @GET("pokemons")
    public fun getPokemons():Call<MutableList<Pokemon>>

}