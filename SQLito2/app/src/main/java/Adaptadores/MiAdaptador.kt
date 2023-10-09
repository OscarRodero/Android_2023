package Adaptadores

import Modelos.Almacen
import Modelos.Usuario
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlito.MainActivity2
import com.example.sqlito.R

class MiAdaptador(var usuarios:ArrayList<Usuario>, var context: Context):RecyclerView.Adapter<MiAdaptador.ViewHolder>() {
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
        val NombreUsuario = view.findViewById(R.id.txtNombre) as TextView
        val EdadUsuario = view.findViewById(R.id.txtEdad) as TextView

        @SuppressLint
        fun bind(item: Usuario, context: Context, position: Int, miAdaptador: MiAdaptador) {
            NombreUsuario.text = item.Nombre
            EdadUsuario.text = item.Edad.toString()

            //Configura el click sobre el cardview
            itemView.setOnClickListener{
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra("usuario", item)
                context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("¡Atención!")
                builder.setMessage("Estás a punto de borrar este usuario, ¿seguro que quieres continuar?")
                builder.setPositiveButton("Aceptar"){dialog, _ ->
                    Almacen.usuarios.removeAt(position)
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

        fun eliminarEntrada(adapter: MiAdaptador, position: Int) {
            adapter.usuarios.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }
}
