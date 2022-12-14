package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.MedicamentoRetrofitRepository
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse

open class MedicamentoRetrofitViewModel : ViewModel() {

    var responseMedicamento: LiveData<ArrayList<MedicamentoResponse>>
    private var repository = MedicamentoRetrofitRepository()

    init {
        responseMedicamento = repository.medicamentoResponse
    }

    fun listaMedicamento(token: String) {
        responseMedicamento = repository.listaMedicamento(token)
    }

    fun listaFiltroMedicamento(nombre: String, token: String) {
        responseMedicamento = repository.listaFiltroMedicamento(nombre, token)
    }
}