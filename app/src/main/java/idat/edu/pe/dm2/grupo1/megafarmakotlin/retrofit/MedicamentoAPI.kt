package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MedicamentoAPI {
    @GET("producto/listar")
    fun listarProducto(@Header("Authorization") token: String): Call<ArrayList<MedicamentoResponse>>

    @GET("producto/filtro")
    fun listarFiltroProducto(@Query("nombre_producto") nombre: String, @Header("Authorization") token: String): Call<ArrayList<MedicamentoResponse>>
}