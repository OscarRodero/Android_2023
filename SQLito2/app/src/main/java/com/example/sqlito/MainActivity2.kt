package com.example.sqlito

import Modelos.Almacen
import Modelos.Usuario
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlito.databinding.ActivityMain2Binding
import java.io.Serializable

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        var usuario: Serializable? = intent.getSerializableExtra("usuario")
        binding.btnCambiar.setOnClickListener(){
            var NuevoNombre = binding.etxtNuevoNombre.text.toString()
            var NuevaEdad = binding.etxtNuevaEdad.text.toString().toInt()
            if(usuario is Usuario){
                val pos = Almacen.usuarios.indexOfFirst { it.Nombre == usuario.Nombre && it.Edad == usuario.Edad }
                if (pos != -1) {
                    // Actualiza los atributos del usuario en la lista
                    Almacen.usuarios[pos].Nombre = NuevoNombre
                    Almacen.usuarios[pos].Edad = NuevaEdad
                }
            }
        }
    }
}