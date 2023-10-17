package com.example.androidktor

import API.ServiceBuilder
import API.UserAPI
import Adaptadores.MiAdaptador
import Modelo.Pokemon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidktor.databinding.ActivityMainBinding
import okhttp3.Call
import okhttp3.Response
import okhttp3.ResponseBody
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var miRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        miRecycleView = binding.miRV as RecyclerView
        miRecycleView.setHasFixedSize(true)
        miRecycleView.layoutManager = LinearLayoutManager(this)

        val miAdaptador = MiAdaptador(obtenerTodosLosPokemons(),this)
        miRecycleView.adapter = miAdaptador

        binding.btnBuscar.setOnClickListener() {
        }
    }

    private fun obtenerTodosLosPokemons(): ArrayList<Pokemon> {
        val lista = ArrayList<Pokemon>()
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getPokemons()
        call.enqueue(object: Callback<ArrayList<Pokemon>>{
            override fun onResponse(call: Call<ArrayList<Pokemon>>, response: Response<ArrayList<Pokemon>>){
                Log.e("oscar", response.code().toString())
                for(post in response.body()!!){
                    lista.add(Pokemon(post.id, post.nombre, post.tipo))
                }
                if(response.isSuccessful){

                }
            }
            override fun onFailure(call: Call<MutableList<Pokemon>>, t:Throwable){
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
        return lista
    }
}
