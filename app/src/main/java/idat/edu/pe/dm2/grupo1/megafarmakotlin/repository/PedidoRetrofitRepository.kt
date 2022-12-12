package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MegaFarmaCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraDetalleClienteRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoRetrofitRepository {

    fun registrarReclamo(view: View, compraDetalleClienteRequest: CompraDetalleClienteRequest, token: String) {
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
                    AppMessage.enviarMensaje(
                        view, "INFO: Pedido realizado con exito",
                        TypeMessage.SUCCESSFULL
                    )
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("ErrorReclamo", t.message.toString())
            }
        })
    }

}