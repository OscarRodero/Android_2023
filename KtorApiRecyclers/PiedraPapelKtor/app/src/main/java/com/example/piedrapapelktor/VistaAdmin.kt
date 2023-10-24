package com.example.piedrapapelktor

import Modelos.User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.piedrapapelktor.databinding.ActivityVistaAdminBinding

class VistaAdmin : AppCompatActivity() {
    lateinit var binding:ActivityVistaAdminBinding
    lateinit var usuario:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVistaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        usuario = intent.getSerializableExtra("usuario") as User
        binding.btnAddUsers.setOnClickListener(){

        }

    }
}