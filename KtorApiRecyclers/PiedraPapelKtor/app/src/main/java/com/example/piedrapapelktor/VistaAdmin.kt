package com.example.piedrapapelktor

import API.ServiceBuilder
import API.UserAPI
import Adaptadores.MiAdaptador
import Modelos.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.piedrapapelktor.databinding.ActivityVistaAdminBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VistaAdmin : AppCompatActivity() {
    lateinit var binding:ActivityVistaAdminBinding
    lateinit var usuario:User
    lateinit var miRecycleView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVistaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        usuario = intent.getSerializableExtra("usuario") as User

        miRecycleView = binding.rvMiRecycle as RecyclerView
        miRecycleView.setHasFixedSize(true)
        miRecycleView.layoutManager = LinearLayoutManager(this)
        var usus = obtenerUsuarios()
        var miAdaptador = MiAdaptador(usus, this)
        miRecycleView.adapter = miAdaptador


        binding.btnAddUsers.setOnClickListener(){
            var intent = Intent(this, AddUser::class.java)
            startActivity(intent)
        }

    }

    private fun obtenerUsuarios(): ArrayList<User> {
        val usuarios = ArrayList<User>()
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.obtenerUsuarios()

        try {
            val response = call.execute()
            if (response.isSuccessful) {
                val usus = response.body()
                if (usus != null) {
                    usuarios.addAll(usus)
                }
            } else {
                Log.e("VistaAdmin", "Error en la solicitud: ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("VistaAdmin", "Error de conexión", e)
        }
        return usuarios
    }

}