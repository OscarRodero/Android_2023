package com.example.dualiza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dualiza.databinding.ActivityElegirEntradaBinding

class ElegirEntrada : AppCompatActivity() {
    lateinit var binding: ActivityElegirEntradaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityElegirEntradaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEntradaElegida.setOnClickListener(){

        }
        binding.btnCancelarEntrada.setOnClickListener(){
               finish()
        }
    }
}