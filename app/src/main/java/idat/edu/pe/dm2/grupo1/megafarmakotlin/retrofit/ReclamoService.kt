package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ReclamoClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ReclamoService {
    @POST("reclamo/registrar")
    fun registrarReclamo(
        @Body reclamoClienteRequest: ReclamoClienteRequest,
        @Header("Authorization") token: String
    ): Call<GlobalResponse>
}