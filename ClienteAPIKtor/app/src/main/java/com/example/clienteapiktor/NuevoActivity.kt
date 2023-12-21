package com.example.clienteapiktor

import Api.ServiceBuilder
import Api.UserAPI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.clienteapiktor.Modelo.Usuario
import com.example.clienteapiktor.databinding.ActivityNuevoBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevoActivity : AppCompatActivity() {
    lateinit var binding:ActivityNuevoBinding
    var contexto = this
    lateinit var operacion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_nuevo)
        binding = ActivityNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        operacion = intent.getStringExtra("operacion").toString()
        val idBuscar = intent.getStringExtra("dni").toString()
        if (operacion.equals("modificar")){
            getBuscarUnUsuario(idBuscar)
            binding.edDNINuevo.isEnabled = false  //No dejamos modificar el DNI que es la clave del registro.
        }

        binding.btnCancelarNuevo.setOnClickListener {
            finish()
        }

        binding.btnAceptarNuevo.setOnClickListener {
            val us = Usuario(
                binding.edDNINuevo.text.toString(),
                binding.edNombreNuevo.text.toString(),
                "",
                binding.edTfnoNuevo.text.toString()
            )


            //--------------- Añadir nuevo registro -----------------
            if (operacion.equals("nuevo")) {
                val request = ServiceBuilder.buildService(UserAPI::class.java)
                val call = request.addUsuario(us)
                call.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.e("Fernando", response.message())
                        Log.e("Fernando", response.code().toString())
                        if (response.code() == 200) {
                            Log.e("Fernando", "Registro efectuado con éxito.")
                            Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Log.e("Fernando", "Algo ha fallado en la inserción: clave duplicada.")
                            Toast.makeText(
                                contexto,
                                "Algo ha fallado en la inserción: clave duplicada",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                            Log.e("Fernando", "Registro efectuado con éxito.")
                            Toast.makeText(contexto, "Registro efectuado con éxito", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Fernando", "Algo ha fallado en la conexión.")
                        Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
            //---------------- Modificar un registro -----------------
            else {
                val request = ServiceBuilder.buildService(UserAPI::class.java)
                val call = request.modUsuario(us, us.dni.toString())
                call.enqueue(object : Callback<ResponseBody> {

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.e("Fernando", response.message())
                        Log.e("Fernando", response.code().toString())
                        if (response.code() == 200) {
                            Log.e("Fernando", "Registro modificado con éxito.")
                            Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Log.e("Fernando", "Algo ha fallado en la modificación.")
                            Toast.makeText(
                                contexto,
                                "Algo ha fallado en la modificación",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        if (response.isSuccessful) { //Esto es otra forma de hacerlo en lugar de mirar el código.
                            Log.e("Fernando", "Registro modificado con éxito.")
                            Toast.makeText(contexto, "Registro modificado con éxito", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.e("Fernando", "Algo ha fallado en la conexión.")
                        Toast.makeText(contexto, "Algo ha fallado en la conexión.", Toast.LENGTH_LONG)
                            .show()
                    }
                })
            }
        }
    }

    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    //---------------------------------------------------------------------------------------------
    fun getBuscarUnUsuario(idBusc:String){
        val request = ServiceBuilder.buildService(UserAPI::class.java)
        val call = request.getUnUsuario(idBusc);

        call.enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                val post = response.body()
                if (post != null) {
                    binding.edDNINuevo.append(post.dni)
                    binding.edNombreNuevo.append(post.nombre)
                    binding.edTfnoNuevo.append(post.tfno)
                }
                else {
                    Toast.makeText(this@NuevoActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    binding.btnAceptarNuevo.isEnabled = false
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Toast.makeText(this@NuevoActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}