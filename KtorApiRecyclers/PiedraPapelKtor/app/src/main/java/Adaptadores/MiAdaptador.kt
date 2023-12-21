package Adaptadores

import API.ServiceBuilder
import API.UserAPI
import Modelos.User
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.piedrapapelktor.DetallesUsuario
import com.example.piedrapapelktor.R
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MiAdaptador(var usuarios: ArrayList<User>, var context: Context):RecyclerView.Adapter<MiAdaptador.ViewHolder>() {
    companion object {
        //Variable que guarda el último objeto seleccionado (-1 si no hay seleccionados)
        var seleccionado:Int = -1
    }
    //Rellena cada CardView con sus correspondientes datos en función de la posición que le toque (mirar getItemCount())
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios.get(position)
        holder.bind(item, context, position, this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Infla cada cardview
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        val viewHolder = ViewHolder(vista)
        return viewHolder
    }

    //Devuelve la cantidad de usuarios en el arraylist que se le pasa al adaptador
    override fun getItemCount(): Int {
        return usuarios.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val NombreUsuario = view.findViewById(R.id.txtCVUsuario) as TextView
        val Correo = view.findViewById(R.id.txtCVCorreo) as TextView
        val PW = view.findViewById(R.id.txtCVPassword) as TextView

        @SuppressLint
        fun bind(item: User, context: Context, position: Int, miAdaptador: MiAdaptador) {
            NombreUsuario.text = item.Username
            Correo.text = item.Email
            PW.text = item.Password

            //Configura el click sobre el cardview
            itemView.setOnClickListener{
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Detalles del usuario")
                builder.setMessage("¿Deseas ver los detalles del usuario ${item.Username}?")
                builder.setPositiveButton("Aceptar") { dialog, _ ->
                    val intent = Intent(context, DetallesUsuario::class.java)
                    context.startActivity(intent)
                }
                builder.setNegativeButton("Cancelar"){dialog, _->
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }

            itemView.setOnLongClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("¡Atención!")
                builder.setMessage("Estás a punto de borrar este usuario, ¿seguro que quieres continuar?")
                builder.setPositiveButton("Aceptar"){dialog, _ ->
                    eliminarEntrada(miAdaptador, position, context)
                    miAdaptador.notifyDataSetChanged()
                    dialog.dismiss()
                }
                builder.setNegativeButton("Cancelar"){dialog, _ ->
                    dialog.dismiss()
                }
                val alertDialog = builder.create()
                alertDialog.show()
                true
            }
        }

        fun eliminarEntrada(adapter: MiAdaptador, position: Int, context: Context) {
            val request = ServiceBuilder.buildService(UserAPI::class.java)
            val call = request.borrarUsuario(adapter.usuarios[position])
            call.enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res != null && res > 0) {
                            adapter.usuarios.removeAt(position)
                            adapter.notifyItemRemoved(position)
                        } else {
                            // Manejar el caso de que no se haya eliminado correctamente
                            Toast.makeText(context, "No se pudo borrar el usuario", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Manejar el caso de una respuesta no exitosa (por ejemplo, error de red)
                        Toast.makeText(context, "Error en la solicitud", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Int>, t: Throwable) {
                    // Manejar el caso de error de conexión
                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
