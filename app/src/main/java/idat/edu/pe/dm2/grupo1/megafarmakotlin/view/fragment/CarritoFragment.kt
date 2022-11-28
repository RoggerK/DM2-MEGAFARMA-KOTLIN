package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.CarritoAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentCarritoBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse


class CarritoFragment : Fragment() {
    private lateinit var binding: FragmentCarritoBinding
    private lateinit var carritoAdapter: CarritoAdapter
    private var listaAgregado = ArrayList<String>()
    private var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //esta se crea varias veces ya que Principal esta en segundo plano
        parentFragmentManager.setFragmentResultListener("llavePrincipal",
            this, FragmentResultListener { requestKey, bundle ->
                listaAgregado = bundle.getStringArrayList("listaAgregado") as ArrayList<String>
                if (listaAgregado.size == 0) {
                    AppMessage.enviarMensaje(
                        requireView(), "No hay productos agregados",
                        TypeMessage.INFO
                    )
                } else {
                    for (medicamento in listaAgregado) {
                        val array = medicamento.split(";")
                        val id = array[0].toInt()
                        val url = array[1]
                        val nombre = array[2]
                        val presentacion = array[3]
                        val precioUnitario = array[4].toDouble()
                        val precioTotal = array[5].toDouble()
                        val pedido = array[6].toInt()
                        listaMedicamentosAgregados.add(
                            MedicamentoResponse(
                                idproducto = id,
                                imagen_producto = url,
                                nombre_producto = nombre,
                                presentacion = presentacion,
                                precio_unitario = precioUnitario,
                                precio_total = precioTotal,
                                pedido = pedido
                            )
                        )
                        mostrarCantidadAgregados()
                        actualizarTotales()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarritoBinding.inflate(inflater, container, false)

        binding.reCarrito.layoutManager = LinearLayoutManager(context)
        carritoAdapter =
            CarritoAdapter(
                listaAgregado, listaMedicamentosAgregados,
                binding.tvTotalPrecioProductos, binding.edtCantidad
            )
        binding.reCarrito.adapter = carritoAdapter

        return binding.root
    }

    private fun mostrarCantidadAgregados() {
        binding.edtCantidad.setText(carritoAdapter.itemCount.toString())
    }

    private fun actualizarTotales() {
        var total = 0.00
        for (medicamento in listaMedicamentosAgregados) {
            total += medicamento.precio_unitario * medicamento.pedido
        }

        binding.tvTotalPrecioProductos.text = total.toString()
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        bundle.putStringArrayList("listaCarrito", listaAgregado)
        parentFragmentManager.setFragmentResult("llaveCarrito", bundle)
    }*/
}