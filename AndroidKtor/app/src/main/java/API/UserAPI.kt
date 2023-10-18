package API

import Modelo.Pokemon
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    @GET("pokemons")
    fun getPokemons():Call<MutableList<Pokemon>>

    @GET("pokemons/{id}")
    fun getPokemon(@Path("id")id:String):Pokemon

    @Headers("Content-Type:application/json")
    @POST("pokemons")
    fun registrarPokemon(@Body info:Pokemon):Call<ResponseBody>


}