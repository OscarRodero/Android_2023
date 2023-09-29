package com.example.primerrecycleview

import Modelo.Almacen
import Modelo.FactoriaListaPersonaje
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.primerrecycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Almacen.personajes = FactoriaListaPersonaje.generaLista(12)
        Log.e("Fernando", Almacen.personajes.toString())
    }
}