package com.example.dualiza

import Modelos.Chatarra
import Modelos.Lote
import Modelos.User
import android.R
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.dualiza.databinding.ActivityCrearLoteBinding
import com.google.android.gms.maps.model.Marker
import java.util.UUID

class CrearLote : AppCompatActivity() {
    lateinit var binding: ActivityCrearLoteBinding
    lateinit var nombre: String
    private val listaChatarra = ArrayList<Chatarra>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearLoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtenemos los datos
        this.nombre = intent.getStringExtra("nombre").toString()

        configurarSpinnerTipos()

        //Declaración de la toolbarCrearLote
        binding.crearLoteToolbar.title = "DUALIZA"
        setSupportActionBar(binding.crearLoteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.crearLoteToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnAgregar.setOnClickListener(){
            if (binding.spinnerTipos.selectedItem != -1 && binding.etxtCantidad.text.toString().toInt() > 0) {
                val nuevaChatarra = Chatarra(
                    binding.spinnerTipos.selectedItem.toString(),
                    binding.etxtDescripcionChatarra.text.toString(),
                    binding.etxtCantidad.text.toString().toInt()
                )
                listaChatarra.add(nuevaChatarra)
                var resumen = binding.etxtResumen.text.toString()
                if (resumen.isNotEmpty()) {
                    val nuevaLinea =
                        "${nuevaChatarra.type}, ${nuevaChatarra.description}, ${binding.etxtCantidad.text.toString()}"
                    val nuevoResumen = "$resumen\n$nuevaLinea"
                    binding.etxtResumen.setText(nuevoResumen)
                } else {
                    val nuevaLinea =
                        "${nuevaChatarra.type}, ${nuevaChatarra.description}, ${binding.etxtCantidad.text.toString()}"
                    binding.etxtResumen.setText(nuevaLinea)
                }
            }
        }
        binding.btnCancelarAgregar.setOnClickListener(){
            finish()
        }
        binding.btnContinuar.setOnClickListener(){
            if(!binding.etxtResumen.text.equals("")){
                val intent = Intent(this, SeleccionUbicacion::class.java)
                startActivityForResult(intent, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                val latitud = data?.getDoubleExtra("Latitud", 0.0)
                val longitud = data?.getDoubleExtra("Longitud", 0.0)
                if (latitud != null && longitud != null) {
                    Log.i("oscar", "Latitud: $latitud, Longitud: $longitud")
                    val nuevoLote = Lote(nombre, listaChatarra,false, latitud, longitud)
                    guardarLoteEnFirestore(nuevoLote)
                } else {
                    Log.e("oscar", "Latitud o Longitud nula")
                }
            }
        }
    }

    private fun guardarLoteEnFirestore(lote: Lote) {
        val nuevoDocumentoId = UUID.randomUUID().toString()
        val documentoLote = db.collection("Lotes").document(nuevoDocumentoId)
        documentoLote.set(lote)
            .addOnSuccessListener {
                Log.i("oscar", "Lote guardado en Firestore con ID: $nuevoDocumentoId")
                finish()
            }
            .addOnFailureListener {
                Log.e("oscar", "Error al guardar el Lote en Firestore", it)
            }
    }

    private fun configurarSpinnerTipos() {
        val spinner = binding.spinnerTipos
        val tipos = ArrayList<String>()

        // Obtener la lista de tipos desde Firestore
        db.collection("Objetos").document("Tipos")
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val tiposList = documentSnapshot.get("Nombre") as ArrayList<String>?
                if (tiposList != null) {
                    tipos.addAll(tiposList)
                    // Configurar el adaptador para el Spinner
                    val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, tipos)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter

                    // Establecer un listener para manejar la selección
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            // Aquí puedes realizar acciones cuando se selecciona un tipo
                            // Por ejemplo, mostrar información relacionada con ese tipo
                        }
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Acciones adicionales si nada está seleccionado
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("oscar", "Error al obtener la lista de tipos desde Firestore", e)
            }
    }
}