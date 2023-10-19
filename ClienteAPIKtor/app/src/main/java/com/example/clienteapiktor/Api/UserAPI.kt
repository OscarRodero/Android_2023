package Api

import com.example.clienteapiktor.Modelo.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("listado")
    fun getUsuarioss(): Call<MutableList<Usuario>>

    @GET("listado/{id}")
    fun getUnUsuario(@Path("id") id:String): Call<Usuario>

    @Headers("Content-Type:application/json")
    @POST("registrar")
    fun addUsuario(@Body info: Usuario) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("login")
    fun loginUsuario(@Body info: Usuario) : Call<Usuario>

    @DELETE("borrar/{dni}")
    fun borrarUsuario(@Path("dni") dni:String) : Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @PUT("modificar/{dni}")
    fun modUsuario(@Body info: Usuario, @Path("dni") dni:String) : Call<ResponseBody>
}