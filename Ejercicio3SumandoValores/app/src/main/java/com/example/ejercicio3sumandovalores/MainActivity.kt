package com.example.ejercicio3sumandovalores

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btnSumar = findViewById<Button>(R.id.btnSumar)
        var resultado = findViewById<TextView>(R.id.result)

        btnSumar.setOnClickListener {
            var numero1 =findViewById<EditText>(R.id.etxtNumero1)
            var numero2 = findViewById<EditText>(R.id.etxtNumero2)
            var num1 = numero1.text.toString()
            var num2 = numero2.text.toString()
            var res:Int = num1.toInt()+num2.toInt()
            resultado.setText(res.toString())
        }

    }
}