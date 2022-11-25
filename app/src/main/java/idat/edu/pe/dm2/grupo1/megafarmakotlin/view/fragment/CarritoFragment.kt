package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.CarritoAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse


class CarritoFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter
    private var listaAgregado =  ArrayList<String>()
    private var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener("llavePrincipal",
            this, FragmentResultListener { requestKey, bundle ->
                listaAgregado = bundle.getStringArrayList("listaAgregado") as ArrayList<String>
                if (listaAgregado.size == 0) {
                    AppMessage.enviarMensaje(requireView(), "No hay productos agregados",
                        TypeMessage.INFO)
                } else {
                    for (medicamento in listaAgregado) {
                        val array = medicamento.split(";")
                        val id = array[0].toInt()
                        val url = array[1]
                        val nombre = array[2]
                        val presentacion = array[3]
                        val precio = array[4].toDouble()
                        val pedido = array[5].toInt()
                        listaMedicamentosAgregados.add(
                            MedicamentoResponse(
                                idproducto = id,
                                imagen_producto = url,
                                nombre_producto = nombre,
                                presentacion = presentacion,
                                precio_unitario = precio,
                                pedido = pedido
                            )
                        )
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_carrito, container, false)
        var edtCantidad = view.findViewById<EditText>(R.id.edtCantidad)
        recyclerView = view.findViewById(R.id.reCarrito)
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)
        carritoAdapter = CarritoAdapter(listaAgregado, listaMedicamentosAgregados, edtCantidad)
        recyclerView.adapter = carritoAdapter

        edtCantidad.setText(carritoAdapter.itemCount.toString())

        return view
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        bundle.putStringArrayList("listaCarrito", listaAgregado)
        parentFragmentManager.setFragmentResult("llaveCarrito", bundle)
    }*/
}