package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.PedidoRetrofitRepository
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.CompraDetalleClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.DetalleClienteRequest

class PedidoRetrofitViewModel : ViewModel() {
    private var repository = PedidoRetrofitRepository()

    fun realizarPedido(
        view: View,
        compraClienteRequest: CompraClienteRequest,
        listaDetalle: ArrayList<DetalleClienteRequest>,
        token: String
    ) {
        repository.registrarReclamo(
            view,
            CompraDetalleClienteRequest(compraClienteRequest, listaDetalle),
            token
        )
    }
}