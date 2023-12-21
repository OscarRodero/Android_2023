package Adaptadores

import Modelo.Pokemon
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidktor.R

class MiAdaptador(var pokemons:MutableList<Pokemon>, var context: Context):RecyclerView.Adapter<MiAdaptador.ViewHolder>() {
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
        val Datos = view.findViewById(R.id.txtDatos) as TextView

        @SuppressLint
        fun bind(item: Pokemon, context: Context, position: Int, miAdaptador: MiAdaptador) {
            Datos.text = item.name

            //Configura el click sobre el cardview
            itemView.setOnClickListener{

            }

            itemView.setOnLongClickListener {

                true
            }



        }
    }
}
