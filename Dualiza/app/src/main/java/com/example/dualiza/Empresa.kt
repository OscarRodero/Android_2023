package com.example.dualiza

import Adaptadores.MiAdaptador
import Modelos.Almacen
import Modelos.Chatarra
import Modelos.Lote
import Modelos.ProviderType
import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dualiza.databinding.ActivityEmpresaBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class Empresa: AppCompatActivity() {
    private lateinit var binding: ActivityEmpresaBinding
    private lateinit var bitmap: Bitmap
    private var uriImagen: Uri? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    lateinit var miRVEmpresa: RecyclerView
    private lateinit var miAdaptador: MiAdaptador
    //------------------------- Funciones de callback para activities results -------------------------
    // Activity para lanzar la cámara.
    private val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data!!
                val bitmap = data.extras!!.get("data") as Bitmap
                val nuevoAncho = 200
                val nuevoAlto = 200
                val bitmapAjustado = ajustarTamañoImagen(bitmap, nuevoAncho, nuevoAlto)
                binding.btnLogoEmpresa.setImageBitmap(bitmapAjustado)
                subirFoto(bitmapAjustado)
            }
        }

    // Activity para pedir permisos de cámara.
    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                openCamera.launch(intent)
            } else {
                Log.e("Fernando", "Permiso de cámara no concedido")
            }
        }

    //Activity para lanzar la galería de imágenes.
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            Log.d("oscar", "Selected URI: $uri")
            val bit = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val nuevoAncho = 200
            val nuevoAlto = 200
            val bitmapAjustado = ajustarTamañoImagen(bit, nuevoAncho, nuevoAlto)
            binding.btnLogoEmpresa.setImageBitmap(bitmapAjustado)
            subirFoto(bitmapAjustado)
        } else {
            Log.d("oscar", "No media selected")
        }
    }
    // Función para subir la foto a Firestore
    private fun subirFoto(bitmap: Bitmap) {
        Log.i("oscar", "Entro en subirFoto()")
        val email = firebaseAuth.currentUser?.email
        if (email != null) {
            Log.i("oscar", "Entro en el if")
            val imageRef = storageRef.child("imagenes/$email/logo.jpg")

            // Convertir la imagen a bytes
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            // Subir la imagen al almacenamiento de Firebase
            val uploadTask = imageRef.putBytes(data)
            uploadTask.addOnSuccessListener {
                // La imagen se ha subido correctamente, ahora actualizamos Firestore
                actualizarFirestoreConImagen(email)
            }.addOnFailureListener {
                Log.e("oscar", "Error al subir la imagen al almacenamiento de Firebase: ${it.message}")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        //Cargo la imagen de perfil
        cargarImagenDesdeFirebase()

        // Obtenemos los datos
        val email = intent.getStringExtra("email")
        val nombre = intent.getStringExtra("nombre")
        val contraseña = intent.getStringExtra("contraseña")
        val proveedor = intent.getStringExtra("proveedor")

//        Recycleview
        miAdaptador = MiAdaptador(ArrayList(), this)
        binding.rvEmpresa.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@Empresa)
            adapter = miAdaptador
        }

        binding.etxtNombreDeEmpresa.setText(nombre)
        Log.e("oscar", "Proveedor en empresa: "+proveedor.toString())

        //Declaración de la toolbarCrearLote
        binding.empresaToolbar.title = "DUALIZA"
        setSupportActionBar(binding.empresaToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.empresaToolbar.setNavigationOnClickListener {
            logOut(proveedor, firebaseAuth)
        }

        binding.btnLogoEmpresa.setOnClickListener() {
            mostrarDialogoSeleccionImagen()
        }

        // Después de inicializar la RecyclerView y el adaptador
        cargarLotesDesdeFirestore(nombre, miAdaptador)
    }

    private fun realizarNuevaEntrega() {
        val email = intent.getStringExtra("email")
        val proveedor = intent.getStringExtra("proveedor")
        val nombre = intent.getStringExtra("nombre")
        val contraseña = intent.getStringExtra("contraseña")

        val intent = Intent(this, CrearLote::class.java)
        intent.putExtra("email", email)
        intent.putExtra("proveedor", proveedor)
        intent.putExtra("nombre", nombre)
        intent.putExtra("contraseña", contraseña)
        startActivity(intent)
    }

    //Menú de puntos
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_empresa, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mnop1 ->{
                realizarNuevaEntrega()
            }
            R.id.mnop2 ->{
                val intent = Intent(this, MenuOpciones::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun cargarImagenDesdeFirebase() {
        val email = firebaseAuth.currentUser?.email
        if (email != null) {
            val imageRef = storageRef.child("imagenes/$email/logo.jpg")
            // Descargar la imagen desde Firebase Storage
            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                // Decodificar bytes en Bitmap y establecer en el ImageView
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                binding.btnLogoEmpresa.setImageBitmap(bitmap)
            }.addOnFailureListener {
                Log.e("oscar", "Error al cargar la imagen desde Firebase Storage: ${it.message}")
            }
        }
    }

    private fun cargarLotesDesdeFirestore(empresaNombre: String?, miAdaptador: MiAdaptador) {
        if (empresaNombre != null) {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val result = withContext(Dispatchers.IO) {
                        // Consulta Firestore para obtener los lotes relacionados con la empresa
                        db.collection("Lotes")
                            .whereEqualTo("empresaEntregante", empresaNombre)
                            .get()
                            .await()
                    }

                    Almacen.entregas.clear() // Limpiar la lista actual antes de agregar nuevos lotes

                    for (document in result) {
                        val empresaEntregante = document.getString("empresaEntregante") ?: ""
                        val estado = document.getBoolean("estado") ?: false
                        val latitud = document.getDouble("latitud") ?: 0.0
                        val longitud = document.getDouble("longitud") ?: 0.0

                        // Obtener la lista de objetos entregados y convertirla a ArrayList<Chatarra>
                        val objetosEntregadosList = document.get("objetosEntregados") as ArrayList<HashMap<String, Any>>?
                        val objetosEntregados = ArrayList<Chatarra>()
                        objetosEntregadosList?.let {
                            for (objetoMap in it) {
                                val type = objetoMap["type"] as String
                                val description = objetoMap["description"] as String
                                val quantity = objetoMap["quantity"] as Long // Nota: En Firestore, los números enteros son Long

                                val chatarra = Chatarra(type, description, quantity.toInt())
                                objetosEntregados.add(chatarra)
                            }
                        }

                        val lote = Lote(empresaEntregante, objetosEntregados, estado, latitud, longitud)
                        Almacen.entregas.add(lote)
                    }

                    // Notificar al adaptador que los datos han cambiado
                    miAdaptador.notifyDataSetChanged()
                } catch (exception: Exception) {
                    Log.e("oscar", "Error al obtener los lotes desde Firestore: ${exception.message}")
                    // Manejar el error, mostrar un mensaje al usuario, etc.
                }
            }
        }
    }


    private fun mostrarDialogoSeleccionImagen() {
        val opciones = arrayOf("Cámara", "Galería")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar imagen desde:")
            .setItems(opciones) { dialog: DialogInterface?, which: Int ->
                when (which) {
                    0 -> {
                        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                    1 -> {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                }
            }

        builder.create().show()
    }

    // Actualizar el campo de imagen en Firestore
    private fun actualizarFirestoreConImagen(email: String) {
        db.collection("Users")
            .document(email)
            .update("imagen", FieldValue.serverTimestamp())
            .addOnSuccessListener {
                Log.d("Fernando", "Campo de imagen actualizado en Firestore")
            }
            .addOnFailureListener {
                Log.e("Fernando", "Error al actualizar el campo de imagen en Firestore: ${it.message}")
            }
    }

    private fun logOut(proveedor: String?, firebaseAuth: FirebaseAuth) {
        if (proveedor == ProviderType.GOOGLE.name){
            firebaseAuth.signOut()
            val signInClient = Identity.getSignInClient(this@Empresa)
            signInClient.signOut()
            finish()
        }else{
            firebaseAuth.signOut()
            finish()
        }
    }

    private fun ajustarTamañoImagen(bitmapOriginal: Bitmap, nuevoAncho: Int, nuevoAlto: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmapOriginal, nuevoAncho, nuevoAlto, false)
    }
}
