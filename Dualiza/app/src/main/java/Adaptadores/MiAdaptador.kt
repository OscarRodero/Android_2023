package Adaptadores

import Modelos.Almacen
import Modelos.Lote
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dualiza.R

class MiAdaptador(var entregas: ArrayList<Lote>, var context: Context) : RecyclerView.Adapter<MiAdaptador.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = entregas[position]
        holder.bind(item, context, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return entregas.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val objetosTextView: TextView = view.findViewById(R.id.etxtObjetos)
        private val estadoTextView: TextView = view.findViewById(R.id.etxtEstado)

        @SuppressLint("SetTextI18n")
        fun bind(item: Lote, context: Context, position: Int) {
            objetosTextView.text = "Objetos: ${item.objetosEntregados.size}"
            estadoTextView.text = "Estado: ${item.estado}"

            itemView.setOnClickListener {
                // Lógica para manejar clics en el CardView
                // Por ejemplo, abrir una nueva actividad
                // o realizar alguna acción específica
            }

            itemView.setOnLongClickListener {
                mostrarDialogoEliminacion(context, position)
                true
            }
        }

        private fun mostrarDialogoEliminacion(context: Context, position: Int) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("¡Atención!")
            builder.setMessage("Estás a punto de borrar este lote, ¿seguro que quieres continuar?")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                eliminarEntrada(position)
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        private fun eliminarEntrada(position: Int) {
            entregas.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}

