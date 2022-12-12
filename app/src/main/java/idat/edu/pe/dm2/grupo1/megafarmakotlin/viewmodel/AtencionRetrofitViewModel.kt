package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.AtencionRetrofitRepository
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ReclamoClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntaResponse

class AtencionRetrofitViewModel : ViewModel() {
    var responseReclamo: LiveData<GlobalResponse>
    var responsePregunta: LiveData<List<PreguntaResponse>>
    private var repository = AtencionRetrofitRepository()

    init {
        responseReclamo = repository.reclamoResponse
        responsePregunta = repository.preguntaResponse
    }

    fun listarPreguntas() {
        responsePregunta = repository.listarPreguntas()
    }

    fun registrarReclamo(asunto: String, descripcion: String, idcliente: Int, token: String) {
        responseReclamo = repository.registrarReclamo(
            ReclamoClienteRequest(
                asunto, descripcion,
                idcliente
            ), token
        )
    }
}