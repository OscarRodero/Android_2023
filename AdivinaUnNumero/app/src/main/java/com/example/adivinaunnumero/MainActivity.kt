import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.AlertDialog
import android.content.DialogInterface
<<<<<<< Updated upstream
import android.util.Log
=======
>>>>>>> Stashed changes
import com.example.adivinaunnumero.R
import com.example.adivinaunnumero.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var x: Int = 0
    var contadorIntentos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
<<<<<<< Updated upstream
        /*
=======
>>>>>>> Stashed changes
        val random = Random()
        x = random.nextInt(100)

        binding.btnComprobar.setOnClickListener {
<<<<<<< Updated upstream
            try{
                val numeroIngresado = binding.txtNumero.text.toString().toInt()
                if (numeroIngresado == x) {
                    mostrarDialog("Felicidades, has ganado")
                } else {
                    contadorIntentos++
                    if (numeroIngresado > x) {
                        binding.imagen.setImageResource(R.drawable.arriba)
                    } else {
                        binding.imagen.setImageResource(R.drawable.abajo)
                    }
=======
            val numeroIngresado = binding.txtNumero.text.toString().toInt()
            if (numeroIngresado == x) {
                mostrarDialog("Felicidades, has ganado")
            } else {
                contadorIntentos++
                if (numeroIngresado > x) {
                    binding.imagen.setImageResource(R.drawable.abajo)
                } else {
                    binding.imagen.setImageResource(R.drawable.arriba)
>>>>>>> Stashed changes
                }
            }catch (e: Exception){
                Log.e("Oscar", "error aquí")
            }

        }

        binding.btnVolverAJugar.setOnClickListener {
            contadorIntentos = 0
            x = random.nextInt(100)
            binding.txtNumero.setText("")
            binding.chkIntentos.isChecked = false
            binding.chkPistas.isChecked = false
        }
    }

    private fun mostrarDialog(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(mensaje)
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val alertDialog = builder.create()
        alertDialog.show()
<<<<<<< Updated upstream
    }

         */
=======
>>>>>>> Stashed changes
    }
}
