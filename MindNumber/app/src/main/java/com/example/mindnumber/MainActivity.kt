package com.example.mindnumber

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.mindnumber.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var x: Int = 0
    var contadorIntentos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val random = Random()
        x = random.nextInt(100)
        var contador = 0
        binding.btnComprobar.setOnClickListener {
            if(binding.chkIntentos.isChecked){
                contadorIntentos++
                if(contador==5){
                    binding.btnComprobar.isEnabled=false
                }
            }
            try{
                val numeroIngresado = binding.txtNumero.text.toString().toInt()
                if (numeroIngresado == x) {
                    mostrarDialog("Felicidades, has ganado")
                } else {
                    if(binding.chkPistas.isChecked)
                    {
                        if (numeroIngresado > x) {
                            binding.imagen.setImageResource(R.drawable.arriba)

                        } else {
                            binding.imagen.setImageResource(R.drawable.abajo)
                        }
                    }
                    else
                    {
                        binding.imagen.setImageResource(0)
                    }
                }
            }catch (e: Exception){
                Log.e("Oscar", "error aquí")
            }

        }

        binding.btnVolverAJugar.setOnClickListener {
            contadorIntentos = 0
            x = random.nextInt(100)
            binding.txtNumero.setText("")
            binding.chkIntentos.isChecked = false
            binding.chkPistas.isChecked = false
        }
    }

    private fun mostrarDialog(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(mensaje)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val alertDialog = builder.create()
        alertDialog.show()
    }
}