package com.example.miejemplofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.miejemplofirebase.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "Óscar"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseauth = FirebaseAuth.getInstance()

        //Recuperamos los datos del login.
        binding.txtEmail.text = intent.getStringExtra("email").toString()
        binding.txtProveedor.text = intent.getStringExtra("provider").toString()
        binding.txtNombre.text = intent.getStringExtra("nombre").toString()

        binding.btCerrarSesion.setOnClickListener {
            Log.e(TAG, firebaseauth.currentUser.toString())
            firebaseauth.signOut()
            finish()
        }
    }
}