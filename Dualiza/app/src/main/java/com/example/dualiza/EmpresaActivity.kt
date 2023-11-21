package com.example.dualiza

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dualiza.databinding.ActivityEmpresaBinding
import com.google.firebase.auth.FirebaseAuth

class EmpresaActivity : AppCompatActivity() {
    lateinit var binding: ActivityEmpresaBinding
    private lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()


    }
}