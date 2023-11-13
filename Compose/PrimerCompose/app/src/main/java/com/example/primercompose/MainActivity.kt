package com.example.primercompose

import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primercompose.ui.theme.PrimerComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimerComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //usoDeBox()
                    usoColumns()
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



@Preview
@Composable
fun usoColumns(){
    var contexto = LocalContext.current
    Column(
        modifier = Modifier
            .background(color = Color.Green)
            .size(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Box(modifier = Modifier
            .background(color = Color.Cyan)
            .fillMaxWidth()
            .weight(1f),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Texto 1")
        }
        Box(modifier = Modifier
            .background(color = Color.Red)
            .fillMaxWidth()
            .weight(1.5f),
            contentAlignment = Alignment.CenterEnd
        ){
            Text(text = "Texto 2")
        }
        Box(modifier = Modifier
            .background(color = Color.DarkGray)
            .fillMaxWidth()
            .weight(0.75f),
            contentAlignment = Alignment.CenterStart
        ){
            Text(text = "Texto 3")
        }
        Box(modifier = Modifier
            .background(color = Color.Magenta)
            .fillMaxWidth()
            .weight(1.5f),
            contentAlignment = Alignment.Center
        ){
            Text(text = "Texto 4")
            Button(onClick = { Toast.makeText(contexto, "Me has pulsado", Toast.LENGTH_SHORT).show() }) {
                Text(text = "Púlsame")
            }
        }
    }
}

@Composable
fun usoDeBox(){
    Box(modifier = Modifier
        .background(Color.Cyan)
        .fillMaxWidth()
        .fillMaxHeight()
        ,
        contentAlignment = Alignment.Center
    ){
        Text(text = "Hola a todos")
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = Color.Green)
            ,
            contentAlignment = Alignment.TopCenter
        ){
            Text(text = "Interior")
        }
    }
}