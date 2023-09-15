package com.example.controles1

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btAceptar = findViewById<Button>(R.id.btnAceptar)
        var btBorrar = findViewById<Button>(R.id.btnBorrar)
        var etNombre = findViewById<EditText>(R.id.etxt)

        btAceptar.setOnClickListener{
            Toast.makeText(this, "Hola ${etNombre.text}", Toast.LENGTH_SHORT).show()
            Log.e("Óscar", etNombre.text.toString())
        }
        btBorrar.setOnClickListener { etNombre.setText("") }
    }
}