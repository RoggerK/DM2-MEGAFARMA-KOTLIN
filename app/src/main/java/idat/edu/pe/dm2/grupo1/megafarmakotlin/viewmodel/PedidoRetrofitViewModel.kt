package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.PedidoRetrofitRepository
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraDetalleClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.DetalleClienteRequest

class PedidoRetrofitViewModel : ViewModel() {
    private var repository = PedidoRetrofitRepository()

    fun realizarPedido(
        context: Context,
        compraClienteRequest: CompraClienteRequest,
        listaDetalle: ArrayList<DetalleClienteRequest>,
        token: String
    ) {
        repository.registrarReclamo(
            context,
            CompraDetalleClienteRequest(compraClienteRequest, listaDetalle),
            token
        )
    }
}