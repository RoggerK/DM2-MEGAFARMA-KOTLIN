package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


class PrincipalFragment : Fragment() {
    private val urlFarma = "https://megafarma.herokuapp.com/megafarma/rest/api/v1/"

    private lateinit var recyclerView: RecyclerView
    var token: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_principal, container, false)
        recyclerView = view.findViewById(R.id.recyclerCarrito)
        llenarlistaMedicamentos(view)
        return view
    }

    private fun llenarlistaMedicamentos(view: View) {
        var listaMedicamento = ArrayList<MedicamentoResponse>()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(urlFarma)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val medicamentoAPI: MedicamentoAPI = retrofit.create(MedicamentoAPI::class.java)

        var call: Call<ArrayList<MedicamentoResponse>> = medicamentoAPI.listarProducto("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJyb2dnZXI5MDgxQGdtYWlsLmNvbSIsInJvbGVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2Njg5OTIwNzksImV4cCI6MTY2ODk5MjQzOX0.Dj6iSlozX3CcPgZVxnwmROkFtBdl3IcUylXWIbzBvTQgYSOdtKlQoIdGHqSGwE8GdRL4iaqBMXBLOBuSCPIPXQ")

        call.enqueue(object : Callback<ArrayList<MedicamentoResponse>> {
            override fun onResponse(
                call: Call<ArrayList<MedicamentoResponse>>,
                response: Response<ArrayList<MedicamentoResponse>>
            ) {
                if (response.isSuccessful) {
                    for (medicamento in response.body()!!) {
                        listaMedicamento.add(medicamento)
                    }

                    recyclerView.layoutManager = LinearLayoutManager(MyApplication.instance)
                    recyclerView.adapter = PrincipalAdapter(listaMedicamento)
                } else {
                    AppMessage.enviarMensaje(view, "Error: Token",
                        TypeMessage.INFO
                    )
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                AppMessage.enviarMensaje(view, "Error: ${t.message}",
                    TypeMessage.INFO
                )
            }
        })
    }
}
