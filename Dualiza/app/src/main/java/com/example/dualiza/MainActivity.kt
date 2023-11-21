package com.example.dualiza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dualiza.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseauth:FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Genero una instancia de FireBaseAuth para hacer el login clásico
        firebaseauth = FirebaseAuth.getInstance()

        //Login clásico
        binding.btnLoginClasico.setOnClickListener {
            if(binding.etxtCorreo.text.isNotEmpty() && binding.etxtContraseA.text.isNotEmpty()){
                firebaseauth.signInWithEmailAndPassword(binding.etxtCorreo.text.toString(), binding.etxtContraseA.text.toString()).addOnCompleteListener(){result->
                    if(result.isSuccessful){
                        val intent = Intent(this, EmpresaActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, "Fallo de conexión", Toast.LENGTH_SHORT).show()
                }
            }
        }

        //Autenticación con google
        val gsoo = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        //Login con google
        binding.btnLoginConGoogle.setOnClickListener(){

        }

    }
}