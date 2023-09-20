package com.example.calculadora

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    lateinit var textoPantalla:EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textoPantalla = findViewById<EditText>(R.id.TextoPantalla)
        textoPantalla.isEnabled = false
        var HayComa = false
        var NumAux=""
        
        var btnSumar  = findViewById<Button>(R.id.btnSumar)
        var btnRestar = findViewById<Button>(R.id.btnRestar)
        var btnMultiplicar = findViewById<Button>(R.id.btnMultiplicar)
        var btnDividir = findViewById<Button>(R.id.btnDividir)
        var btnIgual = findViewById<Button>(R.id.btnIgual)
        var btnComa = findViewById<Button>(R.id.btnComa)
        var btnBorrar = findViewById<Button>(R.id.btnBorrar)
        
        var btnCero= findViewById<Button>(R.id.btn0)
        var btnUno = findViewById<Button>(R.id.btn1)
        var btnDos = findViewById<Button>(R.id.btn2)
        var btnTres = findViewById<Button>(R.id.btn3)
        var btnCuatro = findViewById<Button>(R.id.btn4)
        var btnCinco = findViewById<Button>(R.id.btn5)
        var btnSeis = findViewById<Button>(R.id.btn6)
        var btnSiete = findViewById<Button>(R.id.btn7)
        var btnOcho = findViewById<Button>(R.id.btn8)
        var btnNueve = findViewById<Button>(R.id.btn9)
        
        //Cálculos
        btnSumar.setOnClickListener{
            calcular("sumar")
        }
        btnRestar.setOnClickListener {
            calcular("restar")
        }
        btnMultiplicar.setOnClickListener {
            calcular("multiplicar")
        }
        btnDividir.setOnClickListener {
            calcular("dividir")
        }
        btnIgual.setOnClickListener { 
            calcular("igual")
        }
        btnComa.setOnClickListener {
            if(!HayComa){
                var x = textoPantalla.text.toString()
                x = x+","
                textoPantalla.setText(x)
            }
        }
        btnBorrar.setOnClickListener {
            textoPantalla.setText("")
            NumAux=""
        }

        //Añadir Números
        btnCero.setOnClickListener { agregarNumero(0) }
        btnUno.setOnClickListener { agregarNumero(1)  }
        btnDos.setOnClickListener { agregarNumero(2) }
        btnTres.setOnClickListener { agregarNumero(3) }
        btnCuatro.setOnClickListener { agregarNumero(4) }
        btnCinco.setOnClickListener { agregarNumero(5) }
        btnSeis.setOnClickListener { agregarNumero(6) }
        btnSiete.setOnClickListener { agregarNumero(7) }
        btnOcho.setOnClickListener { agregarNumero(8) }
        btnNueve.setOnClickListener { agregarNumero(9) }

    }

    private fun calcular(s: String) {

    }

    private fun agregarNumero(i: Int) {
        var x = textoPantalla.text.toString()
        var y = x+i.toString()
        textoPantalla.setText(y)
    }
}