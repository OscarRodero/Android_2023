package com.example.encuesta

import Modelos.Encuesta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.encuesta.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.RGSO.check(0)
        binding.btnValidar.setOnClickListener {
            var valido = true
            try {
                //Genero un tipo encuesta
                var Espe = ArrayList<String>()
                var n = Encuesta("",0,Espe,0)

                //Compruebo si el check de anónimo está marcado
                if(binding.SwAnonimo.isChecked){
                    n.Nombre="Anonimo"
                }else{
                    //Si el check de anónimo no está marcado, reviso que haya texto en el nombre, sino, aviso al usuario.
                    if(binding.etxtNombre.text.toString()!=""){
                        n.Nombre=binding.etxtNombre.text.toString()
                    }else{
                        Toast.makeText(this,"Debes indicar un nombre o presentarte como anónimo", Toast.LENGTH_SHORT).show()
                        valido = false
                    }
                }
                n.SO = binding.RGSO.checkedRadioButtonId

            }catch (e:Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }

        }

    }
}