package com.example.esqueletorecycleview

import Adaptadores.MiAdaptador
import Modelos.Almacen
import Modelos.FactoriaUsuarios
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esqueletorecycleview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var miRecycleView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Almacen.usuarios = FactoriaUsuarios.generarUsuarios(10)
        Log.e("Oscar", Almacen.usuarios.toString())

        miRecycleView = binding.rvMiRecycle as RecyclerView
        miRecycleView.setHasFixedSize(true)
        miRecycleView.layoutManager = LinearLayoutManager(this)
        var miAdaptador = MiAdaptador(Almacen.usuarios, this)
        miRecycleView.adapter = miAdaptador

    }
}