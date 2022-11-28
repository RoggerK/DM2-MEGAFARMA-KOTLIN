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
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse

class PrincipalAdapter(var listaMedicamento: ArrayList<MedicamentoResponse>, var listaAgregado: ArrayList<String>) :
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
        val id = listaMedicamento[i].idproducto
        val url = listaMedicamento[i].imagen_producto
        val nombre = listaMedicamento[i].nombre_producto
        val presentacion = listaMedicamento[i].presentacion
        val precio = listaMedicamento[i].precio_unitario

        Picasso.get()
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .into(viewHolder.itemImage)

        //viewHolder.itemAgregar.isEnabled = estaSeleccionado(i)
        //arrayString valor activo para true o false
        viewHolder.itemTitle.text = nombre
        viewHolder.itemDetail.text = presentacion
        viewHolder.itemPrec.text = precio.toString()
        viewHolder.itemAgregar.setOnClickListener(View.OnClickListener {
            viewHolder.itemAgregar.isEnabled = false
            listaAgregado.add("$id;$url;$nombre;$presentacion;$precio;$precio;1")
            AppMessage.enviarMensaje(viewHolder.itemView,"Se añadio ${listaMedicamento[i].nombre_producto}",TypeMessage.SUCCESSFULL)
        })
    }

    override fun getItemCount(): Int {
        return listaMedicamento.size
    }

    /*private fun estaSeleccionado(i: Int): Boolean {
        var respuesta = true
        var array = listaAgregado[i].split(";").toMutableList()
        if(array[0].toInt() == listaMedicamento[i].idproducto) {
            respuesta = false
        }
        return respuesta
    }*/
}