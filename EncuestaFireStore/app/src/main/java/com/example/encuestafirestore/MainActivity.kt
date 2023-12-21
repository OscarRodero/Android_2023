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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

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
            if(binding.etxtCorreo.text.isNotEmpty() && binding.etxtContraseA.text.isNotEmpty()){
                FireBaseAuth.signInWithEmailAndPassword(binding.etxtCorreo.text.toString(), binding.etxtContraseA.text.toString()).addOnCompleteListener{
                    if(it.isSuccessful){
                        var usu :Usuario? = null
                        runBlocking {
                            Log.e("oscar", "Llego aquí")
                            val trabajo : Job = launch(context = Dispatchers.Default) {
                                val querySnapshot = db.collection("users").whereEqualTo("correo", binding.etxtCorreo.text.toString()).get().await()
//                                Log.e("oscar", querySnapshot.toString())
                                for (document in querySnapshot.documents) {
                                    var correo = document.get("correo").toString()
                                    var rol = document.get("rol").toString().toInt()
                                    usu = Usuario(correo, rol)
                                    Log.e("oscar", document.toString())
                                }
                            }
                            //Con este método el hilo principal de onCreate se espera a que la función acabe y devuelva la colección con los datos.
                            trabajo.join() //Esperamos a que el método acabe: https://dzone.com/articles/waiting-for-coroutines
                        }
                        Log.e("oscar", usu.toString())
                        if (usu != null) {
                            if(usu!!.rol == 0){
                                val intent = Intent(this, MainUsuarioEstandar::class.java)
                                intent.putExtra("nombre", usu)
                                startActivity(intent)
                            }else if(usu!!.rol == 1){
                                val intent = Intent(this, MainUsuarioAdmin::class.java)
                                intent.putExtra("nombre", usu)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "No se pudo verificar", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else {
                            Toast.makeText(this, "Usuario encontrado, no tiene datos", Toast.LENGTH_SHORT).show()
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