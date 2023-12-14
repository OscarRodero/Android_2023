package com.example.dualiza

import Modelos.ProviderType
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

        val usuarioActual = Auxiliar.Conexion.obtenerUsuarioActual()
        val configuracion = usuarioActual?.let { Auxiliar.Conexion.obtenerConfiguracion(this, it) }
        Log.e("oscar", configuracion.toString())
        if (configuracion?.sesion == true) {
            // Si la configuración indica que la sesión debe recordarse, ir directamente a la actividad de la empresa
            val intent = Intent(this, Empresa::class.java)
            startActivity(intent)
            finish()
            return
        }

        //Login clásico
        binding.btnLoginClasico.setOnClickListener {
            if(binding.etxtCorreo.text.isNotEmpty() && binding.etxtContraseA.text.isNotEmpty()){
                firebaseauth.signInWithEmailAndPassword(binding.etxtCorreo.text.toString(), binding.etxtContraseA.text.toString()).addOnCompleteListener(){result->
                    if(result.isSuccessful){
                        val intent = Intent(this, Empresa::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                    }
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

        //Registro de usuario
        binding.txtRegistroBasico.setOnClickListener(){
            val intent = Intent(this, RegistroNuevoUsuario::class.java)
            startActivity(intent)
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
        launcherVentanaGoogle.launch(signInClient)
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseauth.signInWithCredential(credential).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                val isNewUser = authTask.result?.additionalUserInfo?.isNewUser ?: false
                if (isNewUser) {
                    val usu = hashMapOf(
                        "email" to account.email.toString(),
                        "nombre" to account.displayName.toString(),
                        "permisos" to listOf(4L),
                        "contraseña" to ""
                    )
                    db.collection("Users")
                        .document(account.email.toString())
                        .set(usu)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error al realizar el registro", Toast.LENGTH_SHORT).show()
                        }
                }
                val contra = binding.etxtContraseA.text.toString()
                verificarYDirigir(account.email.toString(), account.displayName.toString(), contra)
            } else {
                Toast.makeText(this, authTask.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verificarYDirigir(email: String, nombre: String, contraseña: String) {
        db.collection("Users")
            .document(email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documento = task.result
                    if (documento != null && documento.exists()) {
                        val permisos = documento["permisos"] as? List<Long>
                        if (permisos != null) {
                            irHome(email, ProviderType.GOOGLE, nombre, contraseña, permisos)
                        } else {
                            Toast.makeText(this, "La lista de permisos es nula", Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        Toast.makeText(this, "Documento del usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Fallo al obtener el documento del usuario", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun irHome(email: String, provider: ProviderType, nombre: String, contraseña: String, permisos: List<Long>) {
        Log.e("oscar", provider.toString())
        if (permisos.size > 1) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("¿Cómo deseas acceder?")
            for (permiso in permisos) {
                when (permiso) {
                    1L -> alertDialogBuilder.setPositiveButton("Administrador") { _, _ ->
                        abrirVentana(email, provider, nombre, contraseña, Administradores::class.java)
                    }
                    2L -> alertDialogBuilder.setNegativeButton("Clasificador") { _, _ ->
                        abrirVentana(email, provider, nombre, contraseña, Clasificadores::class.java)
                    }
                    3L -> alertDialogBuilder.setNeutralButton("Diseñador") { _, _ ->
                        abrirVentana(email, provider, nombre, contraseña, Diseniadores::class.java)
                    }
                    4L -> alertDialogBuilder.setNegativeButton("Empresa") { _, _ ->
                        abrirVentana(email, provider, nombre, contraseña, Empresa::class.java)
                    }
                }
            }
            alertDialogBuilder.show()
        } else if (permisos.size == 1) {
            val permiso = permisos[0]
            if (permiso != null) {
                when (permiso) {
                    1L -> abrirVentana(email, provider, nombre, contraseña, Administradores::class.java)
                    2L -> abrirVentana(email, provider, nombre, contraseña, Clasificadores::class.java)
                    3L -> abrirVentana(email, provider, nombre, contraseña, Diseniadores::class.java)
                    4L -> abrirVentana(email, provider, nombre, contraseña, Empresa::class.java)
                    else -> {
                        Toast.makeText(this, "Permiso no manejado: $permiso", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "El permiso no es un entero", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "La lista de permisos está vacía", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirVentana(email: String, provider: ProviderType, nombre: String, contrasenia: String, claseVentana: Class<*>) {
        Log.e("oscar", provider.name)
        try{
            val intent = Intent(this, claseVentana).apply {
                putExtra("email", email)
                putExtra("proveedor", provider.name)
                putExtra("nombre", nombre)
                putExtra("contraseña", contrasenia)
            }
            startActivity(intent)
        }catch(e: Exception){
            Log.e("oscar", "Error al abrir ventana: ${e.javaClass.simpleName}, ${e.message}", e)
            e.printStackTrace()
        }
    }

}