package com.example.dualiza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dualiza.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseauth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Genero una instancia de FireBaseAuth para hacer el login clásico
        firebaseauth = FirebaseAuth.getInstance()


        binding.btnLoginClasico.setOnClickListener {

        }
    }
}