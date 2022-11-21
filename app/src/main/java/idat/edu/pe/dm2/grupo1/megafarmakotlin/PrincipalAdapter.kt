package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.MedicamentoResponse

class PrincipalAdapter(var listaMedicamento: ArrayList<MedicamentoResponse>) :
    RecyclerView.Adapter<PrincipalAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var itemPrec: TextView
        var itemAgregar: Button

        init {
            itemImage = itemView.findViewById(R.id.imageProducto1)
            itemTitle = itemView.findViewById(R.id.tvProducto1)
            itemDetail = itemView.findViewById(R.id.tvProducto2)
            itemPrec = itemView.findViewById(R.id.tvProducto3)
            itemAgregar = itemView.findViewById(R.id.btAgregar)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_productos, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = listaMedicamento[i].nombre_producto
        viewHolder.itemDetail.text = listaMedicamento[i].presentacion
        viewHolder.itemPrec.text = listaMedicamento[i].precio_unitario.toString()
        viewHolder.itemAgregar.setOnClickListener(View.OnClickListener {
            println("Agregaste " + listaMedicamento[i].nombre_producto)
        })

        Picasso.get()
            .load(listaMedicamento[i].imagen_producto)
            .error(R.drawable.ic_launcher_foreground)
            .into(viewHolder.itemImage)
    }

    override fun getItemCount(): Int {
        return listaMedicamento.size
    }
}