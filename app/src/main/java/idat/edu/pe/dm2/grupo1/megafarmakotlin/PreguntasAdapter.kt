package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ItemPreguntasAyudaBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntasResponse

class PreguntasAdapter
    : RecyclerView.Adapter<PreguntasAdapter.ViewHolder>(){

    lateinit var context: Context
    lateinit var cursor: Cursor

    fun RecyclerViewAdapterPreguntas(context: Context, cursor: Cursor){
        this.context = context
        this.cursor = cursor
    }

    override fun onCreateViewHolder(view: ViewGroup, int: Int): ViewHolder {
        val inflater = LayoutInflater.from(view.context)
        return ViewHolder(inflater.inflate(R.layout.item_preguntas_ayuda,
            view, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        cursor.moveToPosition(position)

        holder.tvTitulo.text = cursor.getString(1)
        holder.tvDescripcion.text = cursor.getString(2)
    }

    override fun getItemCount(): Int {
        if (cursor == null)
            return 0
        else
            return cursor.count
    }

        inner class ViewHolder: RecyclerView.ViewHolder{

            val tvTitulo: TextView
            val tvDescripcion: TextView

            constructor(view: View) : super(view){
                val bindingItemsRV = ItemPreguntasAyudaBinding.bind(view)
                tvTitulo = bindingItemsRV.txvPregunta
                tvDescripcion = bindingItemsRV.txvRespuestaPregunta
            }
        }

}

