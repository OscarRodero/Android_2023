package com.example.piedrapapelktor

import API.*
import Modelos.*
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.piedrapapelktor.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAcceder.setOnClickListener{
            if(binding.chkInvitado.isChecked){
                var intent = Intent(this@MainActivity, VistaUser::class.java)
                startActivity(intent)
            }else{
            if(binding.etxtCorreo.text.toString()=="" || binding.etxtPassword.text.toString()==""){
                Toast.makeText(this,"Completa todos los campos",Toast.LENGTH_SHORT).show()
            }else {
                var usuAux = AuxUser(
                    binding.etxtCorreo.text.toString(),
                    binding.etxtPassword.text.toString()
                )
                val request = ServiceBuilder.buildService(UserAPI::class.java)
                val call = request.login(usuAux)
                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        var res = response.body()
                        if (response.isSuccessful) {
                            if (res != null) {
                                if (res.IsAdmin == true) {
                                    var intent = Intent(this@MainActivity, VistaAdmin::class.java)
                                    intent.putExtra("usuario", res)
                                    startActivity(intent)
                                } else {
                                    var intent = Intent(this@MainActivity, VistaUser::class.java)
                                    intent.putExtra("usuario", res)
                                    startActivity(intent)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "Fallo al realizar la conexión",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
            }

        }
        binding.btnSalir.setOnClickListener{
            finish()
        }
    }
}