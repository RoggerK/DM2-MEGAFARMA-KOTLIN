package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.ReclamoRetrofitRepository
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ReclamoClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse

class ReclamoRetrofitViewModel : ViewModel() {
    var responseReclamo: LiveData<GlobalResponse>
    private var repository = ReclamoRetrofitRepository()

    init {
        responseReclamo = repository.reclamoResponse
    }

    fun registrarReclamo(asunto: String, descripcion: String, idcliente: Int, token: String) {
        responseReclamo = repository.registrarReclamo(ReclamoClienteRequest(asunto, descripcion,
            idcliente), token)
    }
}