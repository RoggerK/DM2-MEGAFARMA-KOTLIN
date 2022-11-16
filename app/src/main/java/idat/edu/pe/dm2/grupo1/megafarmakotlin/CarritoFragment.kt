package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication


class CarritoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =inflater.inflate(R.layout.fragment_carrito, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.reCarrito)
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)
        recyclerView.adapter = CarritoAdapter()

        return view
    }
}