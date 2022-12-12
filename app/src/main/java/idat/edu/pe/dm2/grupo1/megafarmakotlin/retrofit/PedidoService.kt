package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraDetalleClienteRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PedidoService {
    @POST("pedido/realizar-pedido")
    fun realizarPedido(
        @Body compraDetalleClienteRequest: CompraDetalleClienteRequest,
        @Header("Authorization") token: String
    ): Call<Void>
}