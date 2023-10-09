package com.example.sqlito

import Modelos.Almacen
import Modelos.Usuario
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlito.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAceptar.setOnClickListener(){
            var nombre = binding.etxtNombre.text.toString()
            var edad = binding.etxtEdad.text.toString().toInt()
            if(nombre!="" && nombre != null && edad!=null){
                val usu = Usuario(nombre, edad)
                Almacen.usuarios.add(usu)
            }
            finish()
        }
        binding.btnCancelar.setOnClickListener(){
            finish()
        }
    }
}