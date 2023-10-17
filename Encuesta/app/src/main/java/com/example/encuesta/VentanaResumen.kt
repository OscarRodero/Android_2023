package com.example.encuesta

import Modelos.Almacen
import Modelos.Encuesta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.encuesta.databinding.ActivityVentanaResumenBinding

class VentanaResumen : AppCompatActivity() {
    lateinit var binding: ActivityVentanaResumenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentanaResumenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var Encuestas=ArrayList<Encuesta>()

        Encuestas = intent.getSerializableExtra("Encuestas") as ArrayList<Encuesta>
        Almacen.ListaDeEncuestas = Encuestas

        for (encuesta in Encuestas) {
            val textoActual = binding.etxtListado.text.toString()
            val nuevoTexto = "$textoActual\n${encuesta.toString()}\n"
            binding.etxtListado.setText(nuevoTexto)
        }

    }
}