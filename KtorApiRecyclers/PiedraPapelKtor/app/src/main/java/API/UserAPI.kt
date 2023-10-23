package API

import Modelos.AuxUser
import Modelos.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    @Headers("Content-Type:application/json")
    @POST("login")
    fun login(@Body info:AuxUser): Call<User>

}