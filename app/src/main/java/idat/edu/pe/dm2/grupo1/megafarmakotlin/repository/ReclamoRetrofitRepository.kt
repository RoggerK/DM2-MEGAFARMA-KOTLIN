package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MegaFarmaCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ReclamoClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReclamoRetrofitRepository {
    var reclamoResponse = MutableLiveData<GlobalResponse>()

    fun registrarReclamo(reclamoClienteRequest: ReclamoClienteRequest, token: String)
            : MutableLiveData<GlobalResponse> {
        val call: Call<GlobalResponse> =
            MegaFarmaCliente.retrofitReclamoService.registrarReclamo(reclamoClienteRequest, token)
        call.enqueue(object : Callback<GlobalResponse> {
            override fun onResponse(
                call: Call<GlobalResponse>,
                response: Response<GlobalResponse>
            ) {
                reclamoResponse.value = response.body()
            }

            override fun onFailure(call: Call<GlobalResponse>, t: Throwable) {
                Log.e("ErrorReclamo", t.message.toString())
            }
        })
        return reclamoResponse
    }
}