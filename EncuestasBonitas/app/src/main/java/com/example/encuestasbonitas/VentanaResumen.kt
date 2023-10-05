package com.example.encuestasbonitas

import Modelos.Encuesta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.encuestasbonitas.databinding.ActivityVentanaResumenBinding
import java.io.Serializable

class VentanaResumen : AppCompatActivity() {
    lateinit var binding: ActivityVentanaResumenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVentanaResumenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var ListaEncuestas = ArrayList<Encuesta>()
        ListaEncuestas = intent.getSerializableExtra("Encuestas") as ArrayList<Encuesta>
        if (ListaEncuestas != null) {
            val encuestasText = buildString {
                for (encuesta in ListaEncuestas) {
                    append(encuesta.toString())
                    append("\n\n")
                }
            }
            binding.etxtListado.setText(encuestasText)
        } else {
            binding.etxtListado.setText("No se encontraron encuestas")
        }
    }
}