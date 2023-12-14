package com.example.dualiza

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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.dualiza.databinding.ActivityDiseniadoresBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class Diseniadores : AppCompatActivity() {
    lateinit var binding: ActivityDiseniadoresBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    private lateinit var bitmap: Bitmap
    private var uriImagen: Uri? = null

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
                binding.btnImagenPerfilDiseniador.setImageBitmap(bitmapAjustado)
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
                Log.e("oscar", "Permiso de cámara no concedido")
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
            binding.btnImagenPerfilDiseniador.setImageBitmap(bitmapAjustado)
            subirFoto(bitmapAjustado)
        } else {
            Log.d("oscar", "No media selected")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseniadoresBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        //Cargo la imagen de perfil
        cargarImagenDesdeFirebase()

        //Declaración de la toolbarCrearLote
        binding.diseniadoresToolbar.title = "DUALIZA"
        setSupportActionBar(binding.diseniadoresToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtenemos los datos
        val email = intent.getStringExtra("email")
        val nombre = intent.getStringExtra("nombre")
        val contraseña = intent.getStringExtra("contraseña")
        val proveedor = intent.getStringExtra("proveedor")

        binding.btnImagenPerfilDiseniador.setOnClickListener(){
            mostrarDialogoSeleccionImagen()
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
    private fun cargarImagenDesdeFirebase() {
        val email = firebaseAuth.currentUser?.email
        if (email != null) {
            val imageRef = storageRef.child("imagenes/$email/logo.jpg")

            // Descargar la imagen desde Firebase Storage
            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
                // Decodificar bytes en Bitmap y establecer en el ImageView
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                // Ajustar el tamaño del Bitmap (por ejemplo, a 100x100)
                val nuevoAncho = 200
                val nuevoAlto = 200
                val bitmapAjustado = ajustarTamañoImagen(bitmap, nuevoAncho, nuevoAlto)
                // Establecer el Bitmap ajustado en el ImageButton
                binding.btnImagenPerfilDiseniador.setImageBitmap(bitmapAjustado)
            }.addOnFailureListener {
                Log.e("oscar", "Error al cargar la imagen desde Firebase Storage: ${it.message}")
            }
        }
    }
    private fun ajustarTamañoImagen(bitmapOriginal: Bitmap, nuevoAncho: Int, nuevoAlto: Int): Bitmap {
        val anchoOriginal = bitmapOriginal.width
        val altoOriginal = bitmapOriginal.height
        val proporcion = nuevoAncho.toFloat() / anchoOriginal.toFloat()
        val nuevoAltoProporcional = (altoOriginal * proporcion).toInt()
        return Bitmap.createScaledBitmap(bitmapOriginal, nuevoAncho, nuevoAltoProporcional, false)
    }

    // Actualizar el campo de imagen en Firestore
    private fun actualizarFirestoreConImagen(email: String) {
        db.collection("Users")
            .document(email)
            .update("imagen", FieldValue.serverTimestamp())
            .addOnSuccessListener {
                Log.d("oscar", "Campo de imagen actualizado en Firestore")
            }
            .addOnFailureListener {
                Log.e("oscar", "Error al actualizar el campo de imagen en Firestore: ${it.message}")
            }
    }
}