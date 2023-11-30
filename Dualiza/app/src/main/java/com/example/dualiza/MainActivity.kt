package com.example.dualiza

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dualiza.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //Login con google
        binding.btnLoginConGoogle.setOnClickListener(){
            signInGoogle()
        }

    }


    val launcherVentanaGoogle = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        Log.e("oscar", "Llego aquí")
        if(result.resultCode == Activity.RESULT_OK){
            Log.e("oscar", "Llego aquí 2")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        Log.e("oscar", "llego aquí 3")
        if(task.isSuccessful){
            val account:GoogleSignInAccount? = task.result
            if(account!=null){
                Log.e("oscar", "llego aquí 4")
                updateUI(account)
            }
        }else{
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun signInGoogle() {
        val signInClient = googleSignInClient.signInIntent

    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseauth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                irHome(account.email.toString(), ProviderType.GOOGLE, account.displayName.toString())
            }else{
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun irHome(email:String, provider: ProviderType, nombre:String = "Usuario"){
        Log.e("oscar","Valores: ${email}, ${provider}, ${nombre}")
        val homeIntent = Intent(this, EmpresaActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
            putExtra("nombre",nombre)
        }
        startActivity(homeIntent)
    }
}