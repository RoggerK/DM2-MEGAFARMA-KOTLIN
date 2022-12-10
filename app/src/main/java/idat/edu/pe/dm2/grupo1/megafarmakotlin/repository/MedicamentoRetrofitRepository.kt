package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MegaFarmaCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicamentoRetrofitRepository {
    var medicamentoResponse = MutableLiveData<ArrayList<MedicamentoResponse>>()

    fun listaMedicamento(token: String): MutableLiveData<ArrayList<MedicamentoResponse>> {
        val call: Call<ArrayList<MedicamentoResponse>> =
            MegaFarmaCliente.retrofitMedicamentoService.listarProducto(token)
        call.enqueue(object : Callback<ArrayList<MedicamentoResponse>> {
            override fun onResponse(
                call: Call<ArrayList<MedicamentoResponse>>,
                response: Response<ArrayList<MedicamentoResponse>>
            ) {
                if (response.isSuccessful) {
                    medicamentoResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                Log.e("ErrorMedicamento", t.message.toString())
            }
        })
        return medicamentoResponse
    }

    fun listaFiltroMedicamento(
        nombre: String,
        token: String
    ): MutableLiveData<ArrayList<MedicamentoResponse>> {
        val call: Call<ArrayList<MedicamentoResponse>> =
            MegaFarmaCliente.retrofitMedicamentoService.listarFiltroProducto(nombre, token)
        call.enqueue(object : Callback<ArrayList<MedicamentoResponse>> {
            override fun onResponse(
                call: Call<ArrayList<MedicamentoResponse>>,
                response: Response<ArrayList<MedicamentoResponse>>
            ) {
                if (response.isSuccessful) {
                    medicamentoResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<ArrayList<MedicamentoResponse>>, t: Throwable) {
                Log.e("ErrorMedicamento", t.message.toString())
            }
        })
        return medicamentoResponse
    }
}