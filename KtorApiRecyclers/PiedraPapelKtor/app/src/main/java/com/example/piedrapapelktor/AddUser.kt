package com.example.piedrapapelktor

import API.ServiceBuilder
import API.UserAPI
import Modelos.User
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.piedrapapelktor.databinding.ActivityAddUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUser : AppCompatActivity() {
    lateinit var binding:ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardarNuevoUsu.setOnClickListener(){
            if(binding.etxtNuevoNombre.text.isEmpty()|| binding.etxtNuevoCorreo.text.isEmpty() || binding.etxtNuevaContra.text.isEmpty()){
                Toast.makeText(this, "Es necesario rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }else{
                var valor:Boolean = false
                if(binding.switchNuevoAdmin.isChecked){
                    valor = true
                }
                var usu = User(binding.etxtNuevoNombre.text.toString(), binding.etxtNuevoCorreo.text.toString(), binding.etxtNuevaContra.text.toString(), valor)
                val request = ServiceBuilder.buildService(UserAPI::class.java)
                val call = request.register(usu)
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        var res = response.body()
                        if (response.isSuccessful) {
                            if(res !=null){
                                Toast.makeText(this@AddUser, res.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@AddUser,"Fallo al realizar la conexión",Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }

        binding.btnCancelarNuevoUsu.setOnClickListener{
            finish()
        }
    }
}