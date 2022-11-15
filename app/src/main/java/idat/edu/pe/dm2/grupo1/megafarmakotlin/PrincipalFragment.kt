package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication


class PrincipalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_principal, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.recyclerCarrito)
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)
        recyclerView.adapter = PrincipalAdapter()

        return view
    }
}
