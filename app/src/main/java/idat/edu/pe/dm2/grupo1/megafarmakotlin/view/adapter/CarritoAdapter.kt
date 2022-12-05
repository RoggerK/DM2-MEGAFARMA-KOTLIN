package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import java.math.RoundingMode
import java.text.DecimalFormat

class CarritoAdapter(
    var listaAgregados: ArrayList<String>,
    var listaMedicamentosAgregados: ArrayList<MedicamentoResponse>,
    var tvTotalPrecioProductos: TextView,
    var edtCantidad: EditText,
) :
    RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {
    private val df = DecimalFormat("#.##")

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

            df.roundingMode = RoundingMode.DOWN
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
            actualizarMontoTotales()
        })

        if (cantidad == 1) viewHolder.itemMenos.isEnabled = false

        viewHolder.itemMas.setOnClickListener(View.OnClickListener {
            aumentarCantidad(i)
            if (listaMedicamentosAgregados[i].pedido != 1) viewHolder.itemMenos.isEnabled = true
            actualizarTotal(i, viewHolder)
            actualizarAgregados(i)
            actualizarMontoTotales()
        })

        viewHolder.itemMenos.setOnClickListener(View.OnClickListener {
            disminuirCantidad(i)
            if (listaMedicamentosAgregados[i].pedido == 1) viewHolder.itemMenos.isEnabled = false
            actualizarTotal(i, viewHolder)
            actualizarAgregados(i)
            actualizarMontoTotales()
        })
    }

    override fun getItemCount(): Int {
        return listaMedicamentosAgregados.size
    }

    private fun eliminarProducto(i: Int) {
        notifyItemRangeRemoved(i, itemCount)
        edtCantidad.setText((itemCount - 1).toString())
        listaAgregados.removeAt(i)
        listaMedicamentosAgregados.removeAt(i)
    }

    private fun aumentarCantidad(i: Int) {
        listaMedicamentosAgregados[i].pedido++
        notifyItemChanged(i)
        //notifyItemRangeChanged(i, itemCount)
    }

    private fun disminuirCantidad(i: Int) {
        if (listaMedicamentosAgregados[i].pedido >= 2) {
            listaMedicamentosAgregados[i].pedido--
            notifyItemChanged(i)
            //notifyItemRangeChanged(i, itemCount)
        }
    }

    private fun actualizarTotal(i: Int, viewHolder: ViewHolder) {
        val total =
            listaMedicamentosAgregados[i].precio_unitario * listaMedicamentosAgregados[i].pedido
        listaMedicamentosAgregados[i].precio_total = total
        val redondeo = df.format(total).toString()
        viewHolder.itemTotal.text = redondeo
    }

    private fun actualizarMontoTotales() {
        val totales = listaMedicamentosAgregados.sumOf { p ->
            p.precio_total
        }
        val redondeo = df.format(totales).toString()
        tvTotalPrecioProductos.text = redondeo
    }

    private fun actualizarAgregados(i: Int) {
        var array = listaAgregados[i].split(";").toMutableList()
        val precioTotal = listaMedicamentosAgregados[i].precio_total
        val pedido = listaMedicamentosAgregados[i].pedido
        array[5] = precioTotal.toString()
        array[6] = pedido.toString()
        listaAgregados[i] = "${array[0]};${array[1]};${array[2]};${array[3]};${array[4]}" +
                ";${array[5]};${array[6]}"
    }
}