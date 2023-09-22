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
        jugar()
    }

    private fun jugar() {
        var random = Random()
        do {
            var x = random.nextInt(100)

        }while(true)
    }
}