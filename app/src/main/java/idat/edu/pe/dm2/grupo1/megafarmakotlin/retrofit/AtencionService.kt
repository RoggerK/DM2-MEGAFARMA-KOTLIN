package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ReclamoClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntaResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AtencionService {
    @GET("pregunta/listar")
    fun listarPregunta(): Call<List<PreguntaResponse>>

    @POST("reclamo/registrar")
    fun registrarReclamo(
        @Body reclamoClienteRequest: ReclamoClienteRequest,
        @Header("Authorization") token: String
    ): Call<GlobalResponse>
}