package Adaptadores

import Modelo.Almacen
import Modelo.Almacen.personajes
import Modelo.Personaje
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.primerrecycleview.MainActivity
import com.example.primerrecycleview.SegundaActivity
import com.example.primerrecycleview.R
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class MiAdaptadorRecycler (var personajes : ArrayList<Personaje>, var  context: Context) : RecyclerView.Adapter<MiAdaptadorRecycler.ViewHolder>(){

    companion object {
        var seleccionado:Int = -1
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personajes.get(position)
        holder.bind(item, context, position, this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        val viewHolder = ViewHolder(vista)

        viewHolder.itemView.setOnClickListener {
            val intent = Intent(context, SegundaActivity::class.java)
            context.startActivity(intent)
        }

        return viewHolder
    }
    override fun getItemCount(): Int {

        return personajes.size
    }


    //--------------------------------- Clase interna ViewHolder -----------------------------------
    /**
     * La clase ViewHolder. No es necesaria hacerla dentro del adapter, pero como van tan ligadas
     * se puede declarar aquí.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nombrePersonaje = view.findViewById(R.id.txtNombre) as TextView
        val tipoPersonaje = view.findViewById(R.id.txtRaza) as TextView
        val avatar = view.findViewById(R.id.imagenCardView) as ImageView

        val btnDetalleEspcifico = view.findViewById<Button>(R.id.btnDetalle) as Button

        @SuppressLint("ResourceAsColor")
        fun bind(pers: Personaje, context: Context, pos: Int, miAdaptadorRecycler: MiAdaptadorRecycler){
            nombrePersonaje.text = pers.nombre
            tipoPersonaje.text = pers.tipo

            if (pers.nombre.equals("Gandalf")){
                val uri = "@drawable/" + pers.imagen
                val imageResource: Int = context.getResources().getIdentifier(uri, null, context.getPackageName())
                var res: Drawable = context.resources.getDrawable(imageResource)
                avatar.setImageDrawable(res)
            }
            else {
                Glide.with(context).load(pers.imagen).into(avatar)
            }

            if (pos == MiAdaptadorRecycler.seleccionado) {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.purple))
                }
                tipoPersonaje.setTextColor(R.color.purple)
            }
            else {
                with(nombrePersonaje) {
                    this.setTextColor(resources.getColor(R.color.black))
                }
                tipoPersonaje.setTextColor(R.color.black)
            }

//            itemView.setOnLongClickListener(View.OnLongClickListener() {
//                Log.e("Fernando","long click")
//            }


            itemView.setOnClickListener {
                if (pos == MiAdaptadorRecycler.seleccionado){
                    MiAdaptadorRecycler.seleccionado = -1
                }
                else {
                    MiAdaptadorRecycler.seleccionado = pos
                    Log.e("Fernando", "Seleccionado: ${Almacen.personajes.get(MiAdaptadorRecycler.seleccionado).toString()}")
                }
                miAdaptadorRecycler.notifyDataSetChanged()

//                val intent = Intent(context, MainActivity2::class.java)
//
//                context.startActivity(intent)

                Toast.makeText(context, "Valor seleccionado " +  MiAdaptadorRecycler.seleccionado.toString(), Toast.LENGTH_SHORT).show()

            }

            itemView.setOnLongClickListener(View.OnLongClickListener {
                Log.e("Fernando","Seleccionado con long click: ${Almacen.personajes.get(pos).toString()}")
                Almacen.personajes.removeAt(pos)
                miAdaptadorRecycler.notifyDataSetChanged()
                true //Tenemos que devolver un valor boolean.
            })
        }
    }
}
