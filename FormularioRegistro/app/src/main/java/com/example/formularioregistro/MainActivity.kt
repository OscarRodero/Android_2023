package com.example.formularioregistro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.formularioregistro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAceptar.setOnClickListener {
            var listaAsignaturas = mutableListOf<String>()
            if(binding.chkANDROID.isChecked){
                listaAsignaturas.add(binding.chkANDROID.text.toString())
            }
            if(binding.chkDESARROLLO.isChecked){
                listaAsignaturas.add(binding.chkDESARROLLO.text.toString())
            }
            if(binding.chkSGE.isChecked){
                listaAsignaturas.add(binding.chkSGE.text.toString())
            }
            var alumno = Modelo.Alumno(binding.etxtNombre.text.toString(),
                binding.etxtApellidos.text.toString(),
                binding.etxtFechaNacimiento.text.toString(),
                binding.radioGroup.checkedRadioButtonId.toString(),
                listaAsignaturas
                )
            Toast.makeText(this, alumno.toString(), Toast.LENGTH_LONG).show()
        }
    }
}