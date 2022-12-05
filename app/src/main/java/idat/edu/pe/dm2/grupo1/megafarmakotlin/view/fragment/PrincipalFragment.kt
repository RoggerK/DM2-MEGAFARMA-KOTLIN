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
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PrincipalAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentPrincipalBinding
//import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.AuthTableController
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.MedicamentoRetrofitViewModel


class PrincipalFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPrincipalBinding
    private lateinit var principalAdapter: PrincipalAdapter
    private lateinit var medicamentoRetrofitViewModel: MedicamentoRetrofitViewModel

    private var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()
    private var listaAgregado = ArrayList<String>()
    var token = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrincipalBinding.inflate(inflater, container, false)
        medicamentoRetrofitViewModel = ViewModelProvider(this)[MedicamentoRetrofitViewModel::class.java]
        binding.imvBuscar.setOnClickListener(this)

        medicamentoRetrofitViewModel.responseMedicamento.observe(viewLifecycleOwner, Observer {
            response -> obtenerDatosMedicamentos(response)
        })

        return binding.root
    }

    private fun obtenerDatosMedicamentos(response: ArrayList<MedicamentoResponse>?) {
        if (response != null) {
            listaMedicamentosAgregados = response
            principalAdapter = PrincipalAdapter(listaMedicamentosAgregados, listaAgregado)
            binding.recyclerCarrito.layoutManager = LinearLayoutManager(context)
            binding.recyclerCarrito.adapter = principalAdapter
        } else {
            AppMessage.enviarMensaje(binding.root, "ERROR: Token invalido",
                TypeMessage.DANGER)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("llavePrincipal",
            this, FragmentResultListener {
                requestKey, bundle ->
                    listaAgregado = bundle.getStringArrayList("listaAgregado") as ArrayList<String>
            }
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle1 = Bundle()
        bundle1.putStringArrayList("listaCarrito", listaAgregado)
        parentFragmentManager.setFragmentResult("llaveCarrito", bundle1)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imvBuscar -> buscarProducto(binding.edBuscarProducto.text.toString().trim())
        }
    }

    private fun buscarProducto(nombre: String) {
        listaMedicamentosAgregados.clear()
        medicamentoRetrofitViewModel.listaFiltroMedicamento(nombre, "Bearer $token")
    }

    fun llenarlistaMedicamentos() {
        listaMedicamentosAgregados.clear()
        medicamentoRetrofitViewModel.listaMedicamento("Bearer $token")
    }
}
