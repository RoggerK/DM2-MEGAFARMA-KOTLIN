package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.PrincipalAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MedicamentoAPI
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PrincipalFragment : Fragment(), View.OnClickListener {
    private lateinit var recyclerPrincipal: RecyclerView
    private lateinit var principalAdapter: PrincipalAdapter
    private lateinit var edBuscarProducto: EditText
    private lateinit var imvBuscar: ImageView

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
        val view = inflater.inflate(R.layout.fragment_principal, container, false)
        edBuscarProducto = view.findViewById(R.id.edBuscarProducto)
        imvBuscar = view.findViewById(R.id.imvBuscar)
        imvBuscar.setOnClickListener(this)

        recyclerPrincipal = view.findViewById(R.id.recyclerCarrito)
        recyclerPrincipal.layoutManager = LinearLayoutManager(MyApplication.instance)
        llenarlistaMedicamentos()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = Bundle()
        bundle.putStringArrayList("listaAgregado", listaAgregado)
        parentFragmentManager.setFragmentResult("llavePrincipal", bundle)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.imvBuscar -> buscarProducto(edBuscarProducto.text.toString().trim())
        }
    }

    private fun buscarProducto(nombre: String) {
        listaMedicamentosAgregados.clear()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(urlFarma)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val medicamentoAPI: MedicamentoAPI = retrofit.create(MedicamentoAPI::class.java)

        var call: Call<ArrayList<MedicamentoResponse>> =
            medicamentoAPI.listarFiltroProducto(nombre, "Bearer $token")

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
                    recyclerPrincipal.adapter = principalAdapter
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

        val medicamentoAPI: MedicamentoAPI = retrofit.create(MedicamentoAPI::class.java)

        var call: Call<ArrayList<MedicamentoResponse>> =
            medicamentoAPI.listarProducto("Bearer $token")

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
                    recyclerPrincipal.adapter = principalAdapter
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
