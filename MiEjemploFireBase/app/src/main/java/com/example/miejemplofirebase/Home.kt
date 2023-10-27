package com.example.miejemplofirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.miejemplofirebase.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import java.util.Random

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "Óscar"
    val db = Firebase.firestore

    var miArray:ArrayList<User> = ArrayList()  //Este será el arrayList que se usará para el adapter del RecyclerView o de la ListView.
    //Valores fake.
    val nombres = listOf("Alvaro","Jose","María","Lorenzo")
    val apellidos = listOf("Montoya","Heredia","Pescailla","Flores")
    val edades = listOf(18, 23, 45, 67, 34, 47, 41)
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

        binding.btGuardar.setOnClickListener {
            //Se guardarán en modo HashMap (clave, valor).
            var user = hashMapOf(
                "provider" to binding.txtProveedor.text,
                "email" to binding.txtEmail.text.toString(),
                "name" to binding.edNombre.text.toString(),
                "age" to binding.edEdad.text.toString(),
                "roles" to arrayListOf(1, 2, 3),
                "timestamp" to FieldValue.serverTimestamp()
            )

            //Si no existe el documento lo crea, si existe lo remplaza.
//            db.collection("users")
//                .document(user.get("email").toString()) //Será la clave del documento.
//                .set(user).addOnSuccessListener {
//                    Toast.makeText(this, "Almacenado",Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener{
//                    Toast.makeText(this, "Ha ocurrido un error",Toast.LENGTH_SHORT).show()
//                }


            //Otra forma
            //Si no existe el documento lo crea, si existe añade otro. Las id serán asignadas automáticamente.
//            db.collection("users")
//                .add(user)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
//                    Log.e("Fernando", "Documento añadido con ID: ${it.id}")
//                }
//                .addOnFailureListener { e ->
//                    Log.w("Fernando", "Error adding document", e.cause)
//                }
            this.crearUnoAutomaticoConIndiceAleatorio()
            this.crearUnoAutomaticoConIndiceNoAleatorio()
            this.crearCamposArray()


            binding.btRecuperar.setOnClickListener {
                var roles : ArrayList<Int>
                //Búsqueda por id del documento.
                db.collection("users").document(binding.txtEmail.text.toString()).get()
                    .addOnSuccessListener {
                        //Si encuentra el documento será satisfactorio este listener y entraremos en él.
                        binding.edNombre.append(it.get("name") as String?)
                        binding.edEdad.append(it.get("age") as String?)
                        if (it.get("roles")!=null) {
                            roles = it.get("roles") as ArrayList<Int>
                            Log.e("Fernando",roles.toString())
                        }
                        else {
                            Log.e("Fernando", "Sin roles")
                        }

                        Toast.makeText(this, "Recuperado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener{
                        Toast.makeText(this, "Algo ha ido mal al recuperar", Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }

    fun crearUnoAutomaticoConIndiceAleatorio(){
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido con ID: ${it.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }

    //----------------------------------------------------------------------------------------
    /**
     * Este método crea datos al azar usando set y poniendo como índice del documento el DNI.
     */
    fun crearUnoAutomaticoConIndiceNoAleatorio(){
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo reemplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }


    //----------------------------------------------------------------------------------------
    /**
     * Este método crea un documento que dentro tiene un campo de tipo array (para los roles).
     */
    fun crearCamposArray(){
        // Create a new user with a first and last name
        val randomNombre = nombres.random()
        val randomApellido = apellidos.random()
        val edadRandom = edades.random()
        val random = Random()
        val DNI = random.nextInt(100)

        var user = hashMapOf(
            "first" to randomNombre,
            "last" to randomApellido,
            "age" to edadRandom,
            "roles" to arrayListOf(1, 2, 3)
        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(DNI.toString())  //Si hubiera un campo duplicado, lo remplaza.
            .set(user)
            .addOnSuccessListener {
                Log.e(TAG, "Documento añadido.")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e.cause)
            }
    }
}