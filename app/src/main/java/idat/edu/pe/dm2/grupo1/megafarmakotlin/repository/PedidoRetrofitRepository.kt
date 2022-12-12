package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MegaFarmaCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraDetalleClienteRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoRetrofitRepository {

    fun registrarReclamo(
        context: Context,
        compraDetalleClienteRequest: CompraDetalleClienteRequest,
        token: String
    ) {
        val call: Call<Void> =
            MegaFarmaCliente.retrofitPedidoService.realizarPedido(
                compraDetalleClienteRequest,
                token
            )
        call.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "INFO: Pedido realizado con exito", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ErrorReclamo", t.message.toString())
            }
        })
    }

}