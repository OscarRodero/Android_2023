package Adaptadores

import Modelo.Pokemon
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apis.MainActivity2
import com.example.apis.R

class MiAdaptador(var pokemons:ArrayList<Pokemon>, var context: Context):RecyclerView.Adapter<MiAdaptador.ViewHolder>() {
    companion object {
        //Variable que guarda el último objeto seleccionado (-1 si no hay seleccionados)
        var seleccionado:Int = -1
    }
    //Rellena cada CardView con sus correspondientes datos en función de la posición que le toque (mirar getItemCount())
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemons.get(position)
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
        return pokemons.size
    }
    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val NombrePokemon = view.findViewById(R.id.txtNombre) as TextView
        val ID_Pokemon = view.findViewById(R.id.txtID) as TextView

        @SuppressLint
        fun bind(item: Pokemon, context: Context, position: Int, miAdaptador: MiAdaptador) {
            NombrePokemon.text = item.name
            ID_Pokemon.text = item.id.toString()

            //Configura el click sobre el cardview
            itemView.setOnClickListener{
                val intent = Intent(context, MainActivity2::class.java)
                context.startActivity(intent)
            }
            /*
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

             */

        }
    }
}
