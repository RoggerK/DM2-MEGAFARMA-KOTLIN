package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.PrincipalAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentPrincipalBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MedicamentoService
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.MedicamentoViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PrincipalFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentPrincipalBinding
    private lateinit var principalAdapter: PrincipalAdapter

    private var urlFarma = "https://megafarma.herokuapp.com/megafarma/rest/api/v1/"
    private var listaMedicamentosAgregados = ArrayList<MedicamentoResponse>()
    private var listaAgregado = ArrayList<String>()
    var token = ""

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener("llaveCarrito",
            this, FragmentResultListener { requestKey, bundle ->
                listaAgregado = bundle.getStringArrayList("listaCarrito") as ArrayList<String>
            })
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPrincipalBinding.inflate(inflater, container, false)
        binding.imvBuscar.setOnClickListener(this)
        llenarlistaMedicamentos()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        bundle.putStringArrayList("listaAgregado", listaAgregado)
        parentFragmentManager.setFragmentResult("llavePrincipal", bundle)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imvBuscar -> buscarProducto(binding.edBuscarProducto.text.toString().trim())
        }
    }

    private fun buscarProducto(nombre: String) {
        listaMedicamentosAgregados.clear()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(urlFarma)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val medicamentoService: MedicamentoService = retrofit.create(MedicamentoService::class.java)

        var call: Call<ArrayList<MedicamentoResponse>> =
            medicamentoService.listarFiltroProducto(nombre, "Bearer $token")

        call.enqueue(object : Callback<ArrayList<MedicamentoResponse>> {
            override fun onResponse(
                call: Call<ArrayList<MedicamentoResponse>>,
                response: Response<ArrayList<MedicamentoResponse>>
            ) {
                if (response.isSuccessful) {
                    for (medicamento in response.body()!!) {
                        listaMedicamentosAgregados.add(medicamento)
                    }

                    principalAdapter = PrincipalAdapter(listaMedicamentosAgregados, listaAgregado)
                    binding.recyclerCarrito.layoutManager = LinearLayoutManager(context)
                    binding.recyclerCarrito.adapter = principalAdapter
                } else {
                    AppMessage.enviarMensaje(
                        requireView(), "Error: Token",
                        TypeMessage.INFO
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                AppMessage.enviarMensaje(
                    requireView(), "Error: ${t.message}",
                    TypeMessage.INFO
                )
            }
        })
    }

    fun llenarlistaMedicamentos() {
        listaMedicamentosAgregados.clear()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(urlFarma)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val medicamentoService: MedicamentoService = retrofit.create(MedicamentoService::class.java)

        var call: Call<ArrayList<MedicamentoResponse>> =
            medicamentoService.listarProducto("Bearer $token")

        call.enqueue(object : Callback<ArrayList<MedicamentoResponse>> {
            override fun onResponse(
                call: Call<ArrayList<MedicamentoResponse>>,
                response: Response<ArrayList<MedicamentoResponse>>
            ) {
                if (response.isSuccessful) {
                    for (medicamento in response.body()!!) {
                        listaMedicamentosAgregados.add(medicamento)
                    }
                    principalAdapter = PrincipalAdapter(listaMedicamentosAgregados, listaAgregado)
                    binding.recyclerCarrito.layoutManager = LinearLayoutManager(context)
                    binding.recyclerCarrito.adapter = principalAdapter
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                AppMessage.enviarMensaje(
                    requireView(), "Error: ${t.message}",
                    TypeMessage.INFO
                )
            }
        })
    }
}
