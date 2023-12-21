package com.example.clienteapiktor

import Api.ServiceBuilder
import Api.UserAPI
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.clienteapiktor.Modelo.Usuario
import com.example.clienteapiktor.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var contexto = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnListarTodos.setOnClickListener {
            var intentV1 = Intent(this, ListadoActivity::class.java)
            intentV1.putExtra("operacion","listar")
            startActivity(intentV1)
        }

        binding.btnLogin.setOnClickListener {
            val us = Usuario(
                binding.edtBuscarId.text.toString(),
                "",
                binding.edPass.text.toString(),
                "",
            )
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.loginUsuario(us)

            var intentV1 = Intent(this, Bienvenido::class.java)


            call.enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    val post = response.body()
                    if (post != null && response.isSuccessful){
                        intentV1.putExtra("usuarioIngresado",post)
                        startActivity(intentV1)
                    }
                    else {
                        Toast.makeText(this@MainActivity, "No se han encontrado resultados", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.btnBuscarId.setOnClickListener {
            var intentV1 = Intent(this, ListadoActivity::class.java)

            if (binding.edtBuscarId.text.trim().isEmpty()){
                Toast.makeText(this, "Rellene el campo con un DNI. Ejemplo: 1A, 2B, etc...", Toast.LENGTH_SHORT).show()
            }
            else {
                intentV1.putExtra("operacion","buscar")
                intentV1.putExtra("valorBuscar",binding.edtBuscarId.text.toString())
                startActivity(intentV1)
            }
        }

        binding.btnNuevo.setOnClickListener {
            var intentV1 = Intent(this, NuevoActivity::class.java)
            intentV1.putExtra("operacion","nuevo")
            startActivity(intentV1)
        }

        binding.btnModificar.setOnClickListener {
            var intentV1 = Intent(this, NuevoActivity::class.java)
            intentV1.putExtra("operacion","modificar")
            intentV1.putExtra("dni",binding.edtBuscarId.text.toString())
            startActivity(intentV1)
        }

        binding.btnEliminar.setOnClickListener {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.borrarUsuario(binding.edtBuscarId.text.toString())
            call.enqueue(object : Callback<ResponseBody> {

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.e("Fernando",response.message())
                    Log.e ("Fernando", response.code().toString())
                    if (response.code() == 200) {
                        Log.e("Fernando","Registro eliminado con éxito.")
                        Toast.makeText(this@MainActivity, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Log.e("Fernando","Algo ha fallado en el borrado: DNI no encontrado.")
                        Toast.makeText(this@MainActivity, "Algo ha fallado en el borrado: DNI no encontrado",Toast.LENGTH_LONG).show()
                    }
                    if (response.isSuccessful){ //Esto es otra forma de hacerlo en lugar de mirar el código.
                        Log.e("Fernando","Registro eliminado con éxito.")
                        Toast.makeText(this@MainActivity, "Registro eliminado con éxito",Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Fernando","Algo ha fallado en la conexión.")
                    Toast.makeText(this@MainActivity, "Algo ha fallado en la conexión.",Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}