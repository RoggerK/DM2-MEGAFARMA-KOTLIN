package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentPedidoBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFragmentCarritoListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFragmentPedidoListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.DetalleClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PedidoAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.PedidoRetrofitViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class PedidoFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPedidoBinding
    private lateinit var listernerPedido: OnFragmentPedidoListerne
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel
    private lateinit var pedidoRetrofitViewModel: PedidoRetrofitViewModel
    private lateinit var authEntity: AuthEntity
    private lateinit var pedidoAdapter: PedidoAdapter
    private val df = DecimalFormat("#.##")
    private var coordenada = ""
    var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listernerPedido = context as OnFragmentPedidoListerne
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar interfaz");
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPedidoBinding.inflate(inflater, container, false)
        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]
        pedidoRetrofitViewModel = ViewModelProvider(this)[PedidoRetrofitViewModel::class.java]

        realizarDatosOperacionales()
        realizarAdapterPedido()
        binding.btnPedido.setOnClickListener(this)

        authSQLiteViewModel.obtener().observe(viewLifecycleOwner, Observer {
            authEntity = it
            binding.idCompradorr.setText("${authEntity.nombre} ${authEntity.apellido}")
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("llaveMapa",
            this, FragmentResultListener { requestKey, bundle ->
                coordenada = bundle.getString("coordenada").toString()

                val bundle = Bundle()
                bundle.putString("coordenada", coordenada)
                parentFragmentManager.setFragmentResult("llaveCliente", bundle)

                binding.edtGeolocalizacion.setText(coordenada)
            }
        )
    }

    override fun onClick(view: View) {
        realizarPedido()
    }

    private fun realizarPedido() {
        if (validarGeolocalizacion()) {
            val listaDetalle = ArrayList<DetalleClienteRequest>()
            listaMedicamentosAgregados.forEach { agregado ->
                val impuesto = agregado.precio_total * 0.18
                val importe = agregado.precio_total - impuesto
                listaDetalle.add(DetalleClienteRequest(agregado.pedido, importe, impuesto,
                    agregado.precio_total, agregado.idproducto))
            }

            pedidoRetrofitViewModel.realizarPedido(
                requireContext(), CompraClienteRequest(
                    binding.edtGeolocalizacion.text.toString().trim(),
                    binding.txvImporte.text.toString().toDouble(),
                    binding.txvIGV.text.toString().toDouble(),
                    binding.txvTotal.text.toString().toDouble(),
                    authEntity.idcliente
                ), listaDetalle, "Bearer ${authEntity.token}"
            )

            listernerPedido.onClickButtonRealizarPedido()
        }
    }

    private fun validarGeolocalizacion(): Boolean {
        var respuesta = true
        if (binding.edtGeolocalizacion.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "INFO: Debe indicar su latitud y longitud en el mapa",
                TypeMessage.INFO
            )
            respuesta = false
        }
        return respuesta
    }

    fun realizarDatosOperacionales() {

        val sumTotal = listaMedicamentosAgregados.sumOf { medicamento ->
            medicamento.precio_total
        }

        val impuesto = sumTotal * 0.18
        val importe = sumTotal - impuesto

        df.roundingMode = RoundingMode.DOWN

        binding.txvImporte.text = df.format(importe).toString()
        binding.txvIGV.text = df.format(impuesto).toString()
        binding.txvTotal.text = df.format(sumTotal).toString()
    }

    fun realizarAdapterPedido() {
        pedidoAdapter = PedidoAdapter(listaMedicamentosAgregados)
        binding.rcvAgregados.layoutManager = LinearLayoutManager(context)
        binding.rcvAgregados.adapter = pedidoAdapter
    }
}