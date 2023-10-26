package com.example.borrame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.borrame.databinding.ActivityHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth : FirebaseAuth
    val TAG = "Fernando"
    //val db = FirebaseFirestore.getInstance()
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para la autenticación, de cualquier tipo.
        firebaseauth = FirebaseAuth.getInstance()

        //Recuperamos los datos del login.
        binding.txtEmail.text = intent.getStringExtra("email").toString()
        binding.txtProveedor.text = intent.getStringExtra("provider").toString()
        binding.txtNombre.text = intent.getStringExtra("nombre").toString()

        binding.btCerrarSesion.setOnClickListener {
//            val prefs: SharedPreferences.Editor? = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
//            prefs?.clear() //Al cerrar sesión borramos los datos
//            prefs?.apply ()
            Log.e(TAG, firebaseauth.currentUser.toString())
            // Olvidar al usuario, limpiando cualquier referencia persistente
            firebaseauth.currentUser?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseauth.signOut()
                    Log.e(TAG,"Cerrada sesión completamente")
                } else {
                    Log.e(TAG,"Hubo algún error al cerrar la sesión")
                }
            }
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
            db.collection("users")
                .document(user.get("email").toString()) //Será la clave del documento.
                .set(user).addOnSuccessListener {
                    Toast.makeText(this, "Almacenado",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this, "Ha ocurrido un error",Toast.LENGTH_SHORT).show()
                }


            //Otra forma
            //Si no existe el documento lo crea, si existe añade otro. Las id serán asignadas automáticamente.
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    Toast.makeText(this, "Almacenado", Toast.LENGTH_SHORT).show()
                    Log.e("Fernando", "Documento añadido con ID: ${it.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("Fernando", "Error adding document", e.cause)
                }

        }

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

        binding.btEliminar.setOnClickListener {
            //Buscamos antes si existe un campo con ese email en un documento.
            val id = db.collection("users").whereEqualTo("email",binding.txtEmail.text.toString())
                .get()
                .addOnSuccessListener {result ->
                    //Con esto borramos el primero.
//                    db.collection("users").document(result.elementAt(0).id).delete().toString()
                    //Con esto borramos todos.
                    for (document in result) {
                        db.collection("users").document(document.id).delete().toString()
                    }

                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "No se ha encontrado el documento a eliminar", Toast.LENGTH_SHORT).show()
                }

        }

        //https://cloud.google.com/firestore/docs/query-data/queries?hl=es-419#kotlin+ktxandroid_3
        binding.btRecuperarTodos.setOnClickListener {
//            var al = ArrayList<String>()
//            db.collection("users")
//                .get()
//                .addOnSuccessListener { result ->
//                    for (document in result) {
//                        al.add(document.data.toString())
//                        Log.d("Fernando", "${document.id} => ${document.data}")
//                    }
//                }
//                .addOnFailureListener { exception ->
//                    Log.w("Fernando", "Error getting documents.", exception)
//                }
//            Log.e("Fernando", al.toString())//Observamos que esto nos da un AL vacío porque la consulta es asíncrona y necesitaremos hacerla con una corrutina.


           //Corrutinas
            var al = ArrayList<String>()
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val querySnapshot = db.collection("users")
                        .get()
                        .await()

                    val results = mutableListOf<String>()

                    for (document in querySnapshot.documents) {
                        Log.d("Fernando", "${document.id} => ${document.data}")
                        al.add(document.data.toString())
                    }

                    // Realiza acciones en el hilo principal
                    launch(Dispatchers.Main) {
                        // Procesa los resultados aquí
                        Log.e("Fernando", "Esto está en el hilo principal rellenado después de la corrutina: ${al.toString()}")
                    }
                } catch (e: Exception) {
                    // Maneja errores aquí
                    e.printStackTrace()
                }
            }

        }

    }
}