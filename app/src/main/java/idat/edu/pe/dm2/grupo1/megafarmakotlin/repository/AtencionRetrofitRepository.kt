package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MegaFarmaCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ReclamoClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntaResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AtencionRetrofitRepository {
    var reclamoResponse = MutableLiveData<GlobalResponse>()
    var preguntaResponse = MutableLiveData<List<PreguntaResponse>>()

    fun listarPreguntas(): MutableLiveData<List<PreguntaResponse>> {
        val call: Call<List<PreguntaResponse>> =
            MegaFarmaCliente.retrofitAtencionService.listarPregunta()
        call.enqueue(object : Callback<List<PreguntaResponse>> {
            override fun onResponse(
                call: Call<List<PreguntaResponse>>,
                response: Response<List<PreguntaResponse>>
            ) {
                preguntaResponse.value = response.body()
            }

            override fun onFailure(call: Call<List<PreguntaResponse>>, t: Throwable) {
                Log.e("ErrorPregunta", t.message.toString())
            }
        })
        return preguntaResponse
    }

    fun registrarReclamo(reclamoClienteRequest: ReclamoClienteRequest, token: String)
            : MutableLiveData<GlobalResponse> {
        val call: Call<GlobalResponse> =
            MegaFarmaCliente.retrofitAtencionService.registrarReclamo(reclamoClienteRequest, token)
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