package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.MedicamentoResponse


class CarritoFragment : Fragment() {

    private val listaAgregados = ArrayList<MedicamentoResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("llavePrincipal",
            this, FragmentResultListener { requestKey, bundle ->
                try {
                    val agregados = bundle.getStringArrayList("listaAgregado") as ArrayList<String>

                    if (agregados.size != 0) {
                        for (medicamento in agregados) {
                            val array = medicamento.split(";")
                            val id = array[0].toInt()
                            val url = array[1]
                            val nombre = array[2]
                            val presentacion = array[3]
                            val precio = array[4].toDouble()
                            listaAgregados.add(
                                MedicamentoResponse(
                                    idproducto = id,
                                    imagen_producto = url,
                                    nombre_producto = nombre,
                                    presentacion = presentacion,
                                    precio_unitario = precio
                                )
                            )
                            //println("$id;$url;$nombre;$presentacion;$precio")
                        }
                    }

                } catch (n: NullPointerException) {
                    AppMessage.enviarMensaje(requireView(), "No hay productos agregados",TypeMessage.INFO)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_carrito, container, false)
        var recyclerView: RecyclerView = view.findViewById(R.id.reCarrito)
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)
        recyclerView.adapter = CarritoAdapter()

        for (medicamento in listaAgregados) {
            println("id: ${medicamento.idproducto} - nombre: ${medicamento.nombre_producto} - precio: ${medicamento.precio_unitario}")
        }

        return view
    }
}