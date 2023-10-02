package com.example.esqueletorecycleview

import Modelos.Almacen
import Modelos.FactoriaUsuarios
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.esqueletorecycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Almacen.usuarios = FactoriaUsuarios.generarUsuarios(10)
        Log.e("Oscar", Almacen.usuarios.toString())
    }
}