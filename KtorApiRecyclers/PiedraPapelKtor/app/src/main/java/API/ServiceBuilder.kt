package API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val cliente = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.2.250:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .client(cliente)
        .build()

    fun <T> buildService(service:Class<T>):T{
        return retrofit.create(service)
    }
}