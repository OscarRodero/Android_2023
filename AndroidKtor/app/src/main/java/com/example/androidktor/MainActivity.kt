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
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var miRecycleView: RecyclerView
    var listaPokemon:MutableList<Pokemon> = MutableList()

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

    fun obtenerTodosLosPokemons(): MutableList<Pokemon> {
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call  = request.getPokemons()
        call.enqueue(object: Callback<MutableList<Pokemon>>{
            override fun onResponse(call: Call<MutableList<Pokemon>>, response: Response<MutableList<Pokemon>>){
                Log.e("oscar", response.code().toString())
                for (post in response.body()!!){
                    listaPokemon.add(Pokemon(post.id, post.name, post.type))
                }
                if(response.isSuccessful){
                    miRecycleView.apply{
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MiAdaptador(listaPokemon,this@MainActivity)
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Pokemon>>, t:Throwable){
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
