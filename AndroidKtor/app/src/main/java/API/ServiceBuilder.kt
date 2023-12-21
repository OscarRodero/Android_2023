package API

import Modelo.Constantes.url
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val cliente = OkHttpClient.Builder().build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(url)//Obtenida desde el fichero Constantes
        .addConverterFactory(GsonConverterFactory.create())
        .client(cliente)
        .build()

    fun <T> buildService(service:Class<T>):T{
        return retrofit.create(service)
    }
}