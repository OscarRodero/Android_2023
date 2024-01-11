package com.example.ejemploclase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.datastore.AWSDataStorePlugin
import com.amplifyframework.datastore.generated.model.Persona
import com.amplifyframework.datastore.generated.model.Profesor
import com.example.ejemploclase.ui.theme.EjemploClaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EjemploClaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Amplify.addPlugin(AWSApiPlugin())
                    Amplify.addPlugin(AWSDataStorePlugin())
                    Amplify.configure(applicationContext)
                    Log.i("oscar", "Todo OK")

                    crearUsuario("Jaime", "Profesor Chatarrero")
                    crearUsuario("Fernando", "Profesor Android")

                    Greeting("Android")
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjemploClaseTheme {
        Greeting("Android")
    }
}

private fun crearUsuario(nombre: String, cargo:String) {
    val u = Profesor.builder().nombre(nombre).cargo(cargo).build()
    Amplify.DataStore.save(
        u,
        { success -> Log.i("oscar", "Saved item: " + success.item().toString()) },
        { error -> Log.e("oscar", "Could not save item to DataStore", error) }
    )
}

private fun listarUsuarios() {
    var pers = ArrayList<Persona>()
    pers.clear()
    Amplify.DataStore.query(
        Persona::class.java,
        { items ->
            while (items.hasNext()) {
                val item = items.next()
                Log.i("Fernando", "Queried item: ${item.toString()}")
                pers.add(item)
            }
        },
        { failure -> Log.e("Fernando", "Could not query DataStore", failure) }
    )
}