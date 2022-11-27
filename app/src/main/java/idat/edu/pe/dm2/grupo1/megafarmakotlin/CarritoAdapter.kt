package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse

class CarritoAdapter(
    var listaAgregados: ArrayList<String>,
    var listaMedicamentosAgregados: ArrayList<MedicamentoResponse>,
    var tvTotalPrecioProductos: TextView,
    var edtCantidad: EditText
) :
    RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemTotal: TextView
        var itemDelete: ImageView
        var itemUni: EditText
        var itemMas: ImageView
        var itemMenos: ImageView
        var itemPrecUni: TextView

        init {
            itemImage = itemView.findViewById(R.id.imgProducto1)
            itemDelete = itemView.findViewById(R.id.imvEliminar)
            itemTitle = itemView.findViewById(R.id.tvNombreProducto1)
            itemTotal = itemView.findViewById(R.id.tvPrecio)
            itemUni = itemView.findViewById(R.id.edtCantidad)
            itemMas = itemView.findViewById(R.id.imvMas)
            itemMenos = itemView.findViewById(R.id.imvMenos)
            itemPrecUni = itemView.findViewById(R.id.tvPrecioUnitario)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_carrito, viewGroup, false)
        edtCantidad.setText(itemCount.toString())
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val url = listaMedicamentosAgregados[i].imagen_producto
        val nombre = listaMedicamentosAgregados[i].nombre_producto
        val precioUnitario = listaMedicamentosAgregados[i].precio_unitario
        val cantidad = listaMedicamentosAgregados[i].pedido
        val total = precioUnitario * cantidad

        listaMedicamentosAgregados[i].precio_total = total

        Picasso.get()
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .into(viewHolder.itemImage)

        viewHolder.itemTitle.text = nombre
        viewHolder.itemTotal.text = total.toString()
        viewHolder.itemUni.setText(cantidad.toString())
        viewHolder.itemPrecUni.text = precioUnitario.toString()

        viewHolder.itemDelete.setOnClickListener(View.OnClickListener {
            eliminarProducto(i)
            actualizarMontoTotal()
        })

        viewHolder.itemMas.setOnClickListener(View.OnClickListener {
            aumentarCantidad(i)
            actualizarTotales(i, viewHolder)
            actualizarMontoTotal()
        })

        viewHolder.itemMenos.setOnClickListener(View.OnClickListener {
            disminuirCantidad(i)
            actualizarTotales(i, viewHolder)
            actualizarMontoTotal()
        })
    }

    override fun getItemCount(): Int {
        return listaMedicamentosAgregados.size
    }

    private fun eliminarProducto(i: Int) {
        notifyItemRangeRemoved(i, itemCount)
        edtCantidad.setText((itemCount - 1).toString())
        //listaAgregados.removeAt(i)
        listaMedicamentosAgregados.removeAt(i)
    }

    private fun aumentarCantidad(i: Int) {
        listaMedicamentosAgregados[i].pedido++
        notifyItemRangeChanged(i, itemCount)
    }

    private fun disminuirCantidad(i: Int) {
        if (listaMedicamentosAgregados[i].pedido >= 2) {
            listaMedicamentosAgregados[i].pedido--
            notifyItemRangeChanged(i, itemCount)
        }
    }

    private fun actualizarTotales(i: Int, viewHolder: ViewHolder) {
        val total
            = listaMedicamentosAgregados[i].precio_unitario * listaMedicamentosAgregados[i].pedido
        listaMedicamentosAgregados[i].precio_total = total
        viewHolder.itemTotal.text = total.toString()
    }

    private fun actualizarMontoTotal() {
        val total = listaMedicamentosAgregados.sumOf {
            p -> p.precio_total
        }

        tvTotalPrecioProductos.text = total.toString()
    }
}