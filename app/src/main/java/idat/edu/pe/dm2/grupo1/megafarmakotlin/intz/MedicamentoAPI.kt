package idat.edu.pe.dm2.grupo1.megafarmakotlin.intz

import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.MedicamentoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface MedicamentoAPI {
    @GET("producto/listar")
    fun listarProducto(@Header("Authorization") token: String): Call<ArrayList<MedicamentoResponse>>
}