package com.example.encuestasbonitas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.encuestasbonitas.databinding.ActivityVentanaResumenBinding

class VentanaResumen : AppCompatActivity() {
    lateinit var binding: ActivityVentanaResumenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentanaResumenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var Listado = intent.getSerializableExtra("Encuestas")


    }
}