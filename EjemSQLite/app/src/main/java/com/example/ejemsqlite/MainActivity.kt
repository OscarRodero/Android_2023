package com.example.ejemsqlite

import Auxiliar.Conexion
import Modelo.Persona
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.ejemsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            if (binding.edDNI.text.toString().trim().isEmpty() || binding.edNombre.text.toString().trim().isEmpty()
                || binding.edEdad.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
            }
            else {
                var pers: Persona = Persona(
                    binding.edDNI.getText().toString(),
                    binding.edNombre.getText().toString(),
                    binding.edEdad.getText().toString().toInt()
                )
                Conexion.addPersona(this, pers)
                binding.edDNI.setText("")
                binding.edNombre.setText("")
                binding.edEdad.setText("")
                Toast.makeText(this, "Persona insertada", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBorrar.setOnClickListener {
            var cant = Conexion.delPersona(this, binding.edDNI.text.toString())
            binding.edDNI.setText("")
            binding.edNombre.setText("")
            binding.edEdad.setText("")
            if (cant == 1)
                Toast.makeText(this, "Se borró la persona con ese DNI", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "No existe una persona con ese DNI", Toast.LENGTH_SHORT).show()
        }

        binding.btnEditar.setOnClickListener {
            if (binding.edDNI.text.toString().trim().isEmpty()|| binding.edNombre.text.toString().trim().isEmpty()
                || binding.edEdad.text.toString().trim().isEmpty()){
                Toast.makeText(this, "Campos en blanco", Toast.LENGTH_SHORT).show()
            }
            else {
                var pers: Persona = Persona(
                    binding.edDNI.getText().toString(),
                    binding.edNombre.getText().toString(),
                    binding. edEdad.getText().toString().toInt()
                )
                var cant = Conexion.modPersona(this, binding.edDNI.text.toString(), pers)
                if (cant == 1)
                    Toast.makeText(this, "Se modificaron los datos", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "No existe una persona con ese DNI", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBuscar.setOnClickListener {
            var p:Persona? = null
            p = Conexion.buscarPersona(this, binding.edDNI.text.toString())
            if (p!=null) {
                binding.edNombre.setText(p.nombre)
                binding.edEdad.setText(p.edad.toString())
            } else {
                Toast.makeText(this, "No existe una persona con ese DNI", Toast.LENGTH_SHORT).show()
            }
        }


        binding.btnListar.setOnClickListener {
            var listado:ArrayList<Persona> = Conexion.obtenerPersonas(this)

            binding.txtListado.setText("")

            if (listado.size==0) {
                Toast.makeText(this, "No existen datos en la tabla", Toast.LENGTH_SHORT).show()
            }
            else {
                for(p in listado){
                    var cadena = p.dni + ", " + p.nombre + ", " + p.edad.toString() + "\r\n"
                    binding.txtListado.append(cadena)
                }
            }
        }

    }
}