package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PrincipalAdapter: RecyclerView.Adapter<PrincipalAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            itemImage = itemView.findViewById(R.id.imageProducto1)
            itemTitle = itemView.findViewById(R.id.tvProducto1)
            itemDetail = itemView.findViewById(R.id.tvProducto2)
            itemDetail = itemView.findViewById(R.id.tvProducto3)

        }
    }

    val titles = arrayOf(
        "Vick 44",
        "Chao Tableta ",
        "Panadol "
    )

    val details = arrayOf(
        "S/ 16.70",
        "S/ 92.50",
        "S/ 14.40"
    )


    val images = intArrayOf(
        R.drawable.jarabe, R.drawable.chao, R.drawable.panadol, R.drawable.mejoral, R.drawable.vita
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_productos, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])

    }

    override fun getItemCount(): Int {
        return titles.size
    }
}