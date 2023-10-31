package com.example.encuestafirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.encuestafirestore.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEnviar.setOnClickListener{
            var encuesta = hashMapOf(
                "nombre" to binding.etxtEnviar.text
            )
            db.collection("Encuestas").add(encuesta.get("nombre").toString()).addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Bien",Toast.LENGTH_LONG).show()
            }.addOnFailureListener{
                Toast.makeText(this@MainActivity, "Mal",Toast.LENGTH_LONG).show()
            }
        }
    }
}