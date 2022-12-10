package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentPedidoBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PedidoAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel
import java.math.RoundingMode
import java.text.DecimalFormat

class PedidoFragment : Fragment() {
    private lateinit var binding: FragmentPedidoBinding
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel
    private lateinit var pedidoAdapter: PedidoAdapter
    private val df = DecimalFormat("#.##")
    var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPedidoBinding.inflate(inflater, container, false)
        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]

        realizarDatosOperacionales()
        realizarAdapterPedido()

        authSQLiteViewModel.obtener().observe(viewLifecycleOwner, Observer {
            binding.idCompradorr.setText("${it.nombre} ${it.apellido}")
        })
        return binding.root
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