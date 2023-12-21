package com.example.varias

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.varias.Modelo.Persona
import com.example.varias.databinding.ActivityVentana2Binding

class Ventana2 : AppCompatActivity() {
    lateinit var binding: ActivityVentana2Binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_ventana2)
        binding = ActivityVentana2Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        var nom = intent.getStringExtra("nombre1")
//        var eda = intent.getStringExtra("edad1")

//        Log.e("Fernando",nom.toString())
//        Log.e("Fernando",eda.toString())

        //Descomentar lo siguiente para el botón de llamada sin esperar datos.
//        Log.e("Fernando",intent.getStringExtra("elena").toString())
//        //binding.txtCaja2.text = nom + " " + eda
//        var p : Persona = intent.getSerializableExtra("obj") as Persona
//        binding.txtCaja2.text = p.toString()


        binding.btVolver.setOnClickListener {
            finish()
        }

        //Devolver datos a la ventana 1 de forma deprecated.
        binding.btDevolverDepre.setOnClickListener {
            // Get the text from the EditText
            val stringToPassBack = binding.edDevolver.text.toString()

            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            intent.putExtra("keyName", stringToPassBack)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        //Devolver datos a la ventana 1 de forma actual.
        binding.btDevolverActual.setOnClickListener {
            // Get the text from the EditText
            val stringToPassBack = binding.edDevolver.text.toString()

            // Put the String to pass back into an Intent and close this activity
            val intent = Intent()
            intent.putExtra("valorEdicionV2", stringToPassBack)
            //val p:Persona = Persona(editText.text.toString())
            //intent.putExtra("objeto",p) //El objeto debe ser serailizable, para ello ver clase Pesona.kt

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }

}