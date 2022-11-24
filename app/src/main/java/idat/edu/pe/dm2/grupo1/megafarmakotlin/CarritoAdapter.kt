package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.MedicamentoResponse

class CarritoAdapter(var listaAgregados: ArrayList<MedicamentoResponse>, var edtCantidad: EditText) :
    RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemPrec: TextView
        var itemDelete: ImageView

        init {
            itemImage = itemView.findViewById(R.id.imgProducto1)
            itemTitle = itemView.findViewById(R.id.tvNombreProducto1)
            itemPrec = itemView.findViewById(R.id.tvPrecio)
            itemDelete = itemView.findViewById(R.id.imvEliminar)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_carrito, viewGroup, false)
        edtCantidad.setText(itemCount.toString())
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val id = listaAgregados[i].idproducto
        val url = listaAgregados[i].imagen_producto
        val nombre = listaAgregados[i].nombre_producto
        val precio = listaAgregados[i].precio_unitario.toString()

        Picasso.get()
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .into(viewHolder.itemImage)

        viewHolder.itemTitle.text = nombre
        viewHolder.itemPrec.text = precio

        viewHolder.itemDelete.setOnClickListener(View.OnClickListener {
            eliminarProducto(i)
        })
    }

    override fun getItemCount(): Int {
        return listaAgregados.size
    }

    private fun eliminarProducto(i: Int) {
        listaAgregados.removeAt(i)
        notifyItemRangeRemoved(i, itemCount)
        edtCantidad.setText(itemCount.toString())
    }
}