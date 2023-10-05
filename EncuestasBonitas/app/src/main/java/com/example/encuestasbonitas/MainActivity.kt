package com.example.encuestasbonitas

import Modelos.Encuesta
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import com.example.encuestasbonitas.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rbMac.isChecked=true

        var ListaEncuestas = ArrayList<Encuesta>()

        binding.SBHorasEstudio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtHoras.setText(binding.SBHorasEstudio.progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.txtHoras.setText(binding.SBHorasEstudio.progress.toString())
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.txtHoras.setText(binding.SBHorasEstudio.progress.toString())
            }
        })

        //BOTÓN VALIDAR
        binding.btnValidar.setOnClickListener {
            var valido = true
            try {
                //Genero un tipo encuesta
                var Espe = ArrayList<String>()
                var n = Encuesta("","",Espe,0)

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
                //Añado el SO favorito
                var radio = binding.RGSO.checkedRadioButtonId
                when(radio){
                    binding.rbMac.id->{
                        n.SO="MAC"
                    }
                    binding.rbLinux.id->{
                        n.SO="Linux"
                    }
                    binding.rbWindows.id->{
                        n.SO="Windows"
                    }
                }
                //Compruebo los check
                if(binding.chkDam.isChecked){
                    Espe.add("DAM")
                }
                if(binding.chkDaw.isChecked){
                    Espe.add("DAW")
                }
                if(binding.chkAsir.isChecked){
                    Espe.add("ASIR")
                }
                //Añado las horas de estudio
                n.HorasEstudio=binding.SBHorasEstudio.progress

                //Añado la encuesta a la lista
                ListaEncuestas.add(n)
            }catch (e:Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }

        }

        //BOTÓN REINICIAR
        binding.btnReiniciar.setOnClickListener {
            binding.etxtNombre.setText("")
            binding.SwAnonimo.isChecked=false
            binding.rbMac.isChecked=true
            binding.rbLinux.isChecked=false
            binding.rbWindows.isChecked=false
            binding.chkDam.isChecked=false
            binding.chkDaw.isChecked=false
            binding.chkAsir.isChecked=false
            binding.SBHorasEstudio.progress=0
            binding.txtHoras.setText("0")
        }

        binding.btnResumen.setOnClickListener {
            val intent = Intent(this, VentanaResumen::class.java)
            intent.putExtra("Encuestas", ListaEncuestas)
            startActivity(intent)
        }
    }
}