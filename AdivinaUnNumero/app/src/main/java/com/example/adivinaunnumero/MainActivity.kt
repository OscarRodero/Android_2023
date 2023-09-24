package com.example.adivinaunnumero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adivinaunnumero.databinding.ActivityMainBinding
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var random = Random()
        var x = random.nextInt(100)
        var contadorIntentos = 0
        binding.btnComprobar.setOnClickListener {
            if(binding.txtNumero.text.toString().toInt() != x){

            }else{
                contadorIntentos++
                if(binding.txtNumero.text.toString().toInt()>x){

                }else{

                }
            }
        }
    }
}