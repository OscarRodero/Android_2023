package com.example.dualiza

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.dualiza.databinding.ActivityEmpresaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class Empresa: AppCompatActivity() {
    private lateinit var binding: ActivityEmpresaBinding
    private lateinit var bitmap: Bitmap
    private var uriImagen: Uri? = null
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference

    //------------------------- Funciones de callback para activities results -------------------------
    // Activity para lanzar la cámara.
    private val openCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data!!
                this.bitmap = data.extras!!.get("data") as Bitmap
                binding.btnLogoEmpresa.setImageBitmap(bitmap)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        // Obtenemos los datos
        val email = intent.getStringExtra("email")
        val provider = intent.getStringExtra("provider")
        val nombre = intent.getStringExtra("nombre")
        val contraseña = intent.getStringExtra("contraseña")

        binding.btnLogoEmpresa.setOnClickListener() {
            mostrarDialogoSeleccionImagen()
            subirFoto()
        }

        binding.btnRealizarNuevaEntrega.setOnClickListener() {
            val intent = Intent(this, CrearLote::class.java)
            intent.putExtra("email", email)
            intent.putExtra("provider", provider)
            intent.putExtra("nombre", nombre)
            intent.putExtra("contraseña", contraseña)
            startActivity(intent)

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
                        val intent =
                            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        startActivityForResult(intent, 1)
                    }
                }
            }

        builder.create().show()
    }

    // Para abrir la galería.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            binding.btnLogoEmpresa.setImageURI(selectedImage)
            // Llamar a la función para subir la foto a Firestore
            subirFoto()
        }
    }

    // Función para subir la foto a Firestore
    private fun subirFoto() {
        val email = firebaseAuth.currentUser?.email
        if (email != null) {
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
                Log.e("Fernando", "Error al subir la imagen al almacenamiento de Firebase: ${it.message}")
            }
        }
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
}
