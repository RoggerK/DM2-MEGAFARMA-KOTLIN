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
        var itemUni: EditText
        var itemMas: ImageView
        var itemMenos: ImageView

        init {
            itemImage = itemView.findViewById(R.id.imgProducto1)
            itemDelete = itemView.findViewById(R.id.imvEliminar)
            itemTitle = itemView.findViewById(R.id.tvNombreProducto1)
            itemPrec = itemView.findViewById(R.id.tvPrecio)
            itemUni = itemView.findViewById(R.id.edtCantidad)
            itemMas = itemView.findViewById(R.id.imvMas)
            itemMenos = itemView.findViewById(R.id.imvMenos)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_carrito, viewGroup, false)
        edtCantidad.setText(itemCount.toString())
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val url = listaAgregados[i].imagen_producto
        val nombre = listaAgregados[i].nombre_producto
        val precio = listaAgregados[i].precio_unitario.toString()
        val unitario = listaAgregados[i].pedido.toString()

        Picasso.get()
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .into(viewHolder.itemImage)

        viewHolder.itemTitle.text = nombre
        viewHolder.itemPrec.text = precio
        viewHolder.itemUni.setText(unitario)

        viewHolder.itemDelete.setOnClickListener(View.OnClickListener {
            eliminarProducto(i)
        })

        viewHolder.itemMas.setOnClickListener(View.OnClickListener {
            aumentarCantidad(i)
        })

        viewHolder.itemMenos.setOnClickListener(View.OnClickListener {
            disminnuirCantidad(i)
        })
    }

    override fun getItemCount(): Int {
        return listaAgregados.size
    }

    private fun eliminarProducto(i: Int) {
        notifyItemRangeRemoved(i, itemCount)
        edtCantidad.setText((itemCount - 1).toString())
        listaAgregados.removeAt(i)
    }

    private fun aumentarCantidad(i: Int) {
        listaAgregados[i].pedido++
        notifyItemRangeChanged(i, itemCount)
    }

    private fun disminnuirCantidad(i: Int) {
        if(listaAgregados[i].pedido >= 2) {
            listaAgregados[i].pedido--
            notifyItemRangeChanged(i, itemCount)
        }
    }
}