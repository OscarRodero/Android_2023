package com.example.dualiza

import Modelos.Configuracion
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import com.example.dualiza.databinding.ActivityMenuOpcionesBinding

class MenuOpciones : AppCompatActivity() {
    lateinit var binding:ActivityMenuOpcionesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuOpcionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Declaración de la toolbarCrearLote
        binding.menuOpcionesToolbar.title = "DUALIZA"
        setSupportActionBar(binding.menuOpcionesToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.menuOpcionesToolbar.setNavigationOnClickListener {
            finish()
        }

        // Obtener la configuración almacenada en SQLite
        val usuarioActual = Auxiliar.Conexion.obtenerUsuarioActual()
        if (usuarioActual != null) {
            val configuracion = Auxiliar.Conexion.obtenerConfiguracion(this, usuarioActual)
            if (configuracion != null) {
                // Configurar los switches según la configuración obtenida
                binding.swModo.isChecked = configuracion.modo
                binding.swSesion.isChecked = configuracion.sesion
            }
        }

        binding.swModo.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        binding.swSesion.setOnCheckedChangeListener { buttonView, isChecked ->  }

        binding.btnGuardarConfiguracion.setOnClickListener() {
            val modo = binding.swModo.isChecked
            val sesion = binding.swSesion.isChecked
            val configuracion = Configuracion(modo, sesion)

            val usuarioActual = Auxiliar.Conexion.obtenerUsuarioActual()
            if (usuarioActual != null) {
                Auxiliar.Conexion.guardarConfiguracion(this@MenuOpciones, configuracion)
            }
        }

    }
}