package com.example.dualiza

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.dualiza.databinding.ActivityRegistroNuevoUsuarioBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

val db = Firebase.firestore
class RegistroNuevoUsuario : AppCompatActivity() {
    lateinit var binding: ActivityRegistroNuevoUsuarioBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancelarRegistro.setOnClickListener(){
            finish()
        }

        binding.btnAceptarRegistro.setOnClickListener(){
            Log.e("oscar", "Entro al botón")
            if(binding.etxtNombreEmpresa.text.isNotEmpty() && binding.etxtCorreoEmpresa.text.isNotEmpty() && binding.etxtContrasenia.text.isNotEmpty()){
                if(binding.etxtContrasenia.text.toString().length<6){
                    Toast.makeText(this, "Contraseña demasiado corta, el mínimo son 6 caracteres.", Toast.LENGTH_LONG).show()
                }else{
                    Log.e("oscar", "Entro al if")
                    val email = binding.etxtCorreoEmpresa.text.toString()
                    val password = binding.etxtContrasenia.text.toString()
                    db.collection("Users")
                        .document(binding.etxtCorreoEmpresa.text.toString())
                        .get()
                        .addOnCompleteListener() { tarea ->
                            if (tarea.isSuccessful) {
                                Log.e("oscar", "Entro al segundo IF")
                                val documento = tarea.result
                                if (documento != null && documento.exists()) {
                                    Log.e("oscar", "El documento existe")
                                    val builder = AlertDialog.Builder(this)
                                    builder.setTitle("Alerta")
                                        .setMessage("El usuario que está ingresando ya existe")
                                    builder.setPositiveButton("Aceptar", null)
                                    builder.show()
                                } else {
                                    Log.e("oscar", "El documento no existe")
                                    val usu = hashMapOf(
                                        "email" to binding.etxtCorreoEmpresa.text.toString(),
                                        "nombre" to binding.etxtNombreEmpresa.text.toString(),
                                        "contraseña" to binding.etxtContrasenia.text.toString(),
                                        "permisos" to listOf(4)
                                    )
                                    db.collection("Users")
                                        .document(binding.etxtCorreoEmpresa.text.toString())
                                        .set(usu)
                                        .addOnSuccessListener {
                                            Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(
                                                this,
                                                "Ha ocurrido un error",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                }
                                Firebase.auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { authTask ->
                                        if (authTask.isSuccessful) {
                                            Log.e("oscar", "Usuario registrado en el servicio de autenticación")
                                            finish()
                                        }else {
                                            Log.e("oscar", "Fallo en el registro del usuario en el servicio de autenticación")
                                        }
                                    }
                            }else{
                                Log.e("oscar", "Fallo de tarea", tarea.exception)
                            }
                        }
                }
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alerta")
                    .setMessage("Debe completar todos los campos.")
                builder.setPositiveButton("Aceptar", null)
                builder.show()
            }
        }
    }
}