package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.MegaFarmaRoomDatabase
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.PreguntaEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.PreguntaSQLiteRepository

class PreguntaSQLiteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PreguntaSQLiteRepository

    init {
        val preguntaDAO = MegaFarmaRoomDatabase.getDatabase(application).preguntaDAO()
        repository = PreguntaSQLiteRepository(preguntaDAO)
    }

    fun obtener(): LiveData<List<PreguntaEntity>> {
        return repository.obtener()
    }
}