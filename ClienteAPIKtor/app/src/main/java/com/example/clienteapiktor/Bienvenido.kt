package com.example.clienteapiktor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.clienteapiktor.Modelo.Usuario
import com.example.clienteapiktor.databinding.ActivityBienvenidoBinding

class Bienvenido : AppCompatActivity() {
    lateinit var binding: ActivityBienvenidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bienvenido)
        binding = ActivityBienvenidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var usuario = intent.getSerializableExtra("usuarioIngresado") as Usuario
        Log.e("Fernando",usuario.toString())
        binding.txtRoles.append(usuario.toString())
    }
}