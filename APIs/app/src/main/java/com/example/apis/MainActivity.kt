package com.example.apis

import API.ServiceBuilder
import API.UserAPI
import Adaptadores.MiAdaptador
import Modelo.Almacen
import Modelo.Pokemon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apis.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var miRecycleView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Almacen.Pokemons = ArrayList()

        miRecycleView = binding.miRV as RecyclerView
        miRecycleView.setHasFixedSize(true)
        miRecycleView.layoutManager = LinearLayoutManager(this)
        var miAdaptador = MiAdaptador(Almacen.Pokemons, this)
        miRecycleView.adapter = miAdaptador

        obtenerTodosLosPokemons()
    }

    private fun obtenerTodosLosPokemons() {

        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getPokemones()
        call.enqueue(object: Callback<MutableList<Pokemon>>{
            override fun onResponse(call: Call<MutableList<Pokemon>>, response: Response<MutableList<Pokemon>>){
                Log.e("oscar", response.code().toString())
                for(post in response.body()!!){
                    Almacen.Pokemons.add(Pokemon(post.id!!, post.name))
                }
                if(response.isSuccessful){
                    binding.miRV.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MiAdaptador(Almacen.Pokemons,this@MainActivity )
                    }
                }
            }
            override fun onFailure(call: Call<MutableList<Pokemon>>, t: Throwable){
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}