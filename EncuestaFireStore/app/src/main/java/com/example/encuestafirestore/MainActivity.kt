package com.example.encuestafirestore

import Modelo.ProviderType
import Modelo.Usuario
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.encuestafirestore.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var FireBaseAuth: FirebaseAuth
    lateinit var binding:ActivityMainBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FireBaseAuth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            Log.e("oscar", "Entro aquí")
            if(binding.etxtCorreo.text.isNotEmpty() && binding.etxtContraseA.text.isNotEmpty()){
                Log.e("oscar", "Entro aquí 2")
                FireBaseAuth.signInWithEmailAndPassword(binding.etxtCorreo.text.toString(), binding.etxtContraseA.text.toString()).addOnCompleteListener{
                    Log.e("oscar", "Entro aquí 3")
                    if(it.isSuccessful){
                        Log.e("oscar", "Entro aquí 4")
                        db.collection("users").document(it.result?.user?.email?:"").get().addOnSuccessListener {result ->
                            var correo = result.get("correo").toString()
                            var contraseña = result.get("contraseña").toString()
                            var rol = result.get("rol").toString().toInt()
                            var usu = Usuario(correo, contraseña, rol)
                            if(rol == 0){
                                val intent = Intent(this, MainUsuarioEstandar::class.java)
                                intent.putExtra("nombre", usu)
                                startActivity(intent)
                            }else if(rol == 1){
                                val intent = Intent(this, MainUsuarioAdmin::class.java)
                                intent.putExtra("nombre", usu)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "No se pudo verificar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }.addOnFailureListener{
                    Log.e("oscar", "On Failure Salta")
                }
            }
        }

        binding.btnRegistro.setOnClickListener {

        }

        binding.btnLoginConGoogle.setOnClickListener {

        }
    }
}