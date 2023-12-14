package com.example.dualiza

import Modelos.Chatarra
import Modelos.Lote
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dualiza.databinding.ActivityCrearLoteBinding

class CrearLote : AppCompatActivity() {
    lateinit var binding: ActivityCrearLoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearLoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Declaración de la toolbarCrearLote
        binding.crearLoteToolbar.title = "DUALIZA"
        setSupportActionBar(binding.crearLoteToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.crearLoteToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnAgregar.setOnClickListener(){
            if(binding.etxtNombreObjeto.text.toString().isNotEmpty() && binding.etxtCantidad.text.toString().toInt()>0){
                var resumen = binding.etxtResumen.text.toString()
                if(resumen.length>0){
                    var nuevaLinea = binding.etxtNombreObjeto.text.toString()+", "+binding.etxtDescripcion.text.toString()+", "+binding.etxtCantidad.text.toString()
                    var NuevoObjeto = Chatarra(binding.etxtNombreObjeto.text.toString(), binding.etxtDescripcion.text.toString())
                    var NuevoResumen = resumen+"\n"+nuevaLinea
                    binding.etxtResumen.setText(NuevoResumen)
                }else{
                    var nuevaLinea = binding.etxtNombreObjeto.text.toString()+", "+binding.etxtDescripcion.text.toString()+", "+binding.etxtCantidad.text.toString()
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
                val Latitud = data?.getStringExtra("Latitud")
                val Longitud = data?.getStringExtra("Latitud")
            }
        }
    }

}