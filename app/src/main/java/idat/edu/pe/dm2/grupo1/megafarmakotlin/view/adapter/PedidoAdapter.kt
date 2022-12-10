package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse

class PedidoAdapter(var listaMedicamento: ArrayList<MedicamentoResponse>) :
    RecyclerView.Adapter<PedidoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemId: TextView
        var itemNombre: TextView
        var itemCantidad: TextView
        var itemImporte: TextView

        init {
            itemId = itemView.findViewById(R.id.txvIdProducto)
            itemNombre = itemView.findViewById(R.id.txvNombre)
            itemCantidad = itemView.findViewById(R.id.txvCantidad)
            itemImporte = itemView.findViewById(R.id.txvImporte)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_pedido_producto, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemId.text = listaMedicamento[position].idproducto.toString()
        holder.itemNombre.text = listaMedicamento[position].nombre_producto
        holder.itemCantidad.text = listaMedicamento[position].pedido.toString()
        holder.itemImporte.text = listaMedicamento[position].precio_total.toString()
    }

    override fun getItemCount(): Int {
        return listaMedicamento.size
    }
}
