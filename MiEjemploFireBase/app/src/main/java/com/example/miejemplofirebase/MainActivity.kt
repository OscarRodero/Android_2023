package com.example.miejemplofirebase

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.miejemplofirebase.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "Óscar"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para la autenticación de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()


        //------------------------------ Autenticación con email y password ------------------------------------
        binding.btRegistrar.setOnClickListener {
            if (binding.edEmail.text.isNotEmpty() && binding.edPass.text.isNotEmpty()){
                firebaseauth.createUserWithEmailAndPassword(binding.edEmail.text.toString(),binding.edPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email, que enviaría una cadena vacía.
                    } else {
                        showAlert()
                    }
                }
            }
        }

        binding.btLogin.setOnClickListener {
            if (binding.edEmail.text.isNotEmpty() && binding.edPass.text.isNotEmpty()){
                firebaseauth.signInWithEmailAndPassword(binding.edEmail.text.toString(),binding.edPass.text.toString()).addOnCompleteListener {
                    if (it.isSuccessful){
                        irHome(it.result?.user?.email?:"",ProviderType.BASIC)  //Esto de los interrogantes es por si está vacío el email.
                    } else {
                        showAlert()
                    }
                }
            }
            else {
                showAlert("Rellene los campos")
            }
        }
        //------------------ Login Google -------------------
        //------------------------------- -Autenticación Google --------------------------------------------------
        firebaseauth.signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.btGoogle.setOnClickListener {
            signInGoogle()
        }

    }


    //******************************* Para el login con Google ******************************
    private lateinit var googleSignInClient: GoogleSignInClient

    private val launcherVentanaGoogle =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        Log.e(TAG,"Llego aquí 2 ${result.data!!.extras.toString()}")
        if (result.resultCode == Activity.RESULT_OK){
            Log.e(TAG,"Llego aquí 3")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        Log.e(TAG,"Llego aquí 4")
        if (task.isSuccessful){
            Log.e(TAG,"Llego aquí 5")
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                Log.e(TAG,"Llego aquí 6")
                updateUI(account)
            }
        }
        else {
            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseauth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful){
                irHome(account.email.toString(),ProviderType.GOOGLE, account.displayName.toString())
            }
            else {
                Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }


    //--------
    private fun signInGoogle(){
        val signInClient = googleSignInClient.signInIntent
        Log.e(TAG,"Llego aquí 1")
        launcherVentanaGoogle.launch(signInClient)
        //milauncherVentanaGoogle.launch(signInClient)
    }

    }





    //************************************** Funciones auxiliares **************************************
    //*********************************************************************************
    private fun showAlert(msg:String = "Se ha producido un error autenticando al usuario"){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //*********************************************************************************
    private fun irHome(email:String, provider:ProviderType, nombre:String = "Usuario"){
        Log.e(TAG,"Valores: ${email}, ${provider}, ${nombre}")
        val homeIntent = Intent(this, Home::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
            putExtra("nombre",nombre)
        }
        startActivity(homeIntent)
    }
}