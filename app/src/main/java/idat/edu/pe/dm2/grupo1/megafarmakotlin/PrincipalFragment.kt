package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.intz.MedicamentoAPI
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.MedicamentoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PrincipalFragment : Fragment(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var principalAdapter: PrincipalAdapter
    private lateinit var edBuscarProducto: EditText
    private lateinit var imvBuscar: ImageView

    private val urlFarma = "https://megafarma.herokuapp.com/megafarma/rest/api/v1/"
    var listaMedicamento = ArrayList<MedicamentoResponse>()
    var listaAgregado = ArrayList<String>()
    var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_principal, container, false)
        edBuscarProducto = view.findViewById(R.id.edBuscarProducto)
        imvBuscar = view.findViewById(R.id.imvBuscar)
        recyclerView = view.findViewById(R.id.recyclerCarrito)
        recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)

        imvBuscar.setOnClickListener(this)

        llenarlistaMedicamentos(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        bundle.putStringArrayList("listaAgregado", listaAgregado)
        parentFragmentManager.setFragmentResult("llavePrincipal", bundle)
    }

    override fun onClick(view: View) {
        when(view.id) {
            R.id.imvBuscar -> buscarProducto(view, edBuscarProducto.text.toString().trim())
        }
    }

    private fun buscarProducto(view: View, nombre: String) {
        listaMedicamento.clear()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(urlFarma)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val medicamentoAPI: MedicamentoAPI = retrofit.create(MedicamentoAPI::class.java)

        var call: Call<ArrayList<MedicamentoResponse>> =
            medicamentoAPI.listarFiltroProducto(nombre,"Bearer $token")

        call.enqueue(object : Callback<ArrayList<MedicamentoResponse>> {
            override fun onResponse(
                call: Call<ArrayList<MedicamentoResponse>>,
                response: Response<ArrayList<MedicamentoResponse>>
            ) {
                if (response.isSuccessful) {
                    for (medicamento in response.body()!!) {
                        listaMedicamento.add(medicamento)
                    }
                    principalAdapter = PrincipalAdapter(listaMedicamento, listaAgregado)
                    recyclerView.adapter = principalAdapter
                } else {
                    AppMessage.enviarMensaje(
                        view, "Error: Token",
                        TypeMessage.INFO
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                AppMessage.enviarMensaje(
                    view, "Error: ${t.message}",
                    TypeMessage.INFO
                )
            }
        })
    }

    private fun llenarlistaMedicamentos(view: View) {
        listaMedicamento.clear()

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
                        listaMedicamento.add(medicamento)
                    }
                    principalAdapter = PrincipalAdapter(listaMedicamento, listaAgregado)
                    recyclerView.adapter = principalAdapter
                } else {
                    AppMessage.enviarMensaje(
                        view, "Error: Token",
                        TypeMessage.INFO
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                AppMessage.enviarMensaje(
                    view, "Error: ${t.message}",
                    TypeMessage.INFO
                )
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}
