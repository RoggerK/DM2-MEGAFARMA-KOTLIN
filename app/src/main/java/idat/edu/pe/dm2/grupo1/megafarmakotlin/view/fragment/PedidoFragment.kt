package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

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
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PedidoAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class PedidoFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPedidoBinding
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel
    private lateinit var pedidoAdapter: PedidoAdapter
    private val df = DecimalFormat("#.##")
    private var coordenada = ""
    var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPedidoBinding.inflate(inflater, container, false)
        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]

        realizarDatosOperacionales()
        realizarAdapterPedido()
        binding.btnPedido.setOnClickListener(this)

        authSQLiteViewModel.obtener().observe(viewLifecycleOwner, Observer {
            binding.idCompradorr.setText("${it.nombre} ${it.apellido}")
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
        if(validarGeolocalizacion()) {
            AppMessage.enviarMensaje(binding.root, "Pedido realizado",
                TypeMessage.SUCCESSFULL)
        }
    }

    private fun validarGeolocalizacion(): Boolean {
        var respuesta = true
        if(binding.edtGeolocalizacion.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(binding.root, "INFO: Debe indicar su latitud y longitud en el mapa",
                TypeMessage.INFO)
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