package com.example.estados

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.estados.ui.theme.EstadosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EstadosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EjemploEstado()
                    MiEdit()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun GreetingPreview() {
    EstadosTheme {
        Greeting("Android")
    }
}

//@Preview
@Composable
fun EjemploEstado() {
    //Op1 No cambia porque al ser composable la vuelve a pintar.
//    var cont = 0
//    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//        Button(onClick = {
//            cont++
//        }) {
//            Text(text = "Pulsar")
//        }
//        Text(text = "He sido pulsado ${cont} veces")
//    }

    //Op3.
//    var cont = remember { mutableStateOf(0) }
//    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//        Button(onClick = {
//            cont.value++
//        }) {
//            Text(text = "Pulsar")
//        }
//        Text(text = "He sido pulsado ${cont.value} veces")
//        //Text(text = "He sido pulsado ${cont.value} veces")
//    }

    //Op3, usar by para evitar el uso de value.
    /*
    Se importará automáticamente esto:
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
     */
    var cont by remember { mutableStateOf(0) }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            cont++
        }) {
            Text(text = "Pulsar")
        }
        Text(text = "He sido pulsado ${cont} veces")
        //Text(text = "He sido pulsado ${cont.value} veces")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MiEdit(){
    //Como las editText cambian de valor tienen asociado un estado por lo que:
    var miEditText by remember { mutableStateOf("") }
    var contexto = LocalContext.current
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        TextField(value = miEditText, onValueChange = {
            //El valor de it será el que hayamos escrito en la editText.
            miEditText = it
        },
            label = {
                Text("Nombre: ")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier.padding(30.dp),
            onClick = {
            Toast.makeText(contexto, "El valor de la caja de texto es: $miEditText",Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Púlsame")
        }
    }
}