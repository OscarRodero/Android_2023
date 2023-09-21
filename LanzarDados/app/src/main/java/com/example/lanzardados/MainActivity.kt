package com.example.lanzardados

import android.R
import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.nombre_del_archivo)
        mediaPlayer.start() // Para reproducir la música


    }
}