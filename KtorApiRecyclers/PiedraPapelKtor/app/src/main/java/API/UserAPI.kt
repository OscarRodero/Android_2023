package API

import Modelos.AuxUser
import Modelos.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    @Headers("Content-Type:application/json")
    @POST("login")
    fun login(@Body info:AuxUser): Call<User>

    @Headers("Content-Type:applicationi/json")
    @DELETE("users")
    fun borrarUsuario(@Body info:User):Call<Int>

    @GET
    fun obtenerUsuarios():Call<ArrayList<User>>

    @Headers("Content-Type:application/json")
    @POST("users")
    fun register(@Body info:User): Call<User>
}