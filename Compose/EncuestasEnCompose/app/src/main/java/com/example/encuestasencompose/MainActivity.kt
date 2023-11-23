package com.example.encuestasencompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.encuestasencompose.ui.theme.EncuestasEnComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EncuestasEnComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaPrincipal() {
    var textoEnCuadroTexto by remember { mutableStateOf("") }
    EncuestasEnComposeTheme {
        Column(
            modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray))
        {
            TextField(
                value = textoEnCuadroTexto,
                onValueChange = { nuevoTexto -> textoEnCuadroTexto = nuevoTexto },
                label = { Text("Ingresa tu respuesta") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            Button(onClick = { /* Manejar clic del botón aquí */ }) {
                Text("¡Haz clic en mí!")
            }
        }
    }
}
