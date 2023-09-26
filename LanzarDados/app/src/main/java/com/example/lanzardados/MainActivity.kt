package com.example.lanzardados

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.lanzardados.databinding.ActivityMainBinding
import java.util.Random
import java.util.Timer
import java.util.TimerTask
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var sonidoDadosMediaPlayer: MediaPlayer
    val imagenes = arrayOf(R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4, R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8, R.drawable.a9, R.drawable.a10, R.drawable.a11, R.drawable.a12, R.drawable.a13, R.drawable.a14, R.drawable.a15, R.drawable.a16, R.drawable.a17, R.drawable.a18, R.drawable.a19, R.drawable.a20)
    val tamanio = 400
    private var puntosJugador = 0
    private var puntosMaquina = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var contador=0

        binding.imagenJugador.setImageResource(R.drawable.a1)
        binding.imagenMaquina.setImageResource(R.drawable.a1)
        binding.imagenJugador.layoutParams.width = tamanio
        binding.imagenJugador.layoutParams.height = tamanio
        binding.imagenMaquina.layoutParams.width = tamanio
        binding.imagenMaquina.layoutParams.height = tamanio

        mediaPlayer = MediaPlayer.create(this, R.raw.arcade)
        sonidoDadosMediaPlayer = MediaPlayer.create(this, R.raw.sonidodados)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        binding.btnLanzar.setOnClickListener {
            contador++
            tirarDados()
            if (contador == 5) {
                binding.btnLanzar.isEnabled = false
                binding.btnVolverAJugar.isEnabled = true
                compararPuntajes()
            }
        }

        binding.btnVolverAJugar.isEnabled=false
        binding.btnVolverAJugar.setOnClickListener {
            contador = 0
            puntosJugador=0
            puntosMaquina=0
            binding.txtPuntosJugador.setText("0")
            binding.txtPuntosMaquina.setText("0")
            binding.imagenJugador.setImageResource(imagenes[0])
            binding.imagenMaquina.setImageResource(imagenes[0])
            binding.btnLanzar.isEnabled = true
            binding.btnVolverAJugar.isEnabled = false
        }
    }

    private fun compararPuntajes() {
        val mensaje: String = if (puntosJugador > puntosMaquina) {
            "¡El jugador ganó!"
        } else if (puntosJugador < puntosMaquina) {
            "¡La máquina ganó!"
        } else {
            "¡Es un empate!"
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun tirarDados() {
        val sonidoDadosMediaPlayer = MediaPlayer.create(this, R.raw.sonidodados)
        sonidoDadosMediaPlayer.start()
        sonidoDadosMediaPlayer.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.release()
        }

        val timer = Timer()
        val handler = Handler(Looper.getMainLooper())

        var imagenJugador: Int = 0
        var imagenMaquina: Int = 0
        var puntajeJugador = 0
        var puntajeMaquina = 0
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    val randomJugador = Random()
                    val randomMaquina = Random()
                    var indiceImagenJugador = randomJugador.nextInt(imagenes.size)
                    var indiceImagenMaquina = randomMaquina.nextInt(imagenes.size)

                    puntajeJugador = indiceImagenJugador
                    puntajeMaquina = indiceImagenMaquina

                    imagenJugador = imagenes[indiceImagenJugador]
                    imagenMaquina = imagenes[indiceImagenMaquina]

                    binding.imagenJugador.layoutParams.width = tamanio
                    binding.imagenJugador.layoutParams.height = tamanio
                    binding.imagenMaquina.layoutParams.width = tamanio
                    binding.imagenMaquina.layoutParams.height = tamanio

                    binding.imagenJugador.setImageResource(imagenJugador)
                    binding.imagenMaquina.setImageResource(imagenMaquina)

                }
            }
        }, 0, 500)

        handler.postDelayed({
            timer.cancel()

            puntosJugador += puntajeJugador +1
            puntosMaquina += puntajeMaquina +1

            binding.txtPuntosJugador.text = puntosJugador.toString()
            binding.txtPuntosMaquina.text = puntosMaquina.toString()
        }, 1000)
    }
    override fun onResume() {
        super.onResume()
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}
