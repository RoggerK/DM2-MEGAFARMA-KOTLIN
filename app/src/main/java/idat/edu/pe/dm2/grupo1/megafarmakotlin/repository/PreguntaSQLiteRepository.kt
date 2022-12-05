package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import androidx.lifecycle.LiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao.PreguntaDAO
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.PreguntaEntity

class PreguntaSQLiteRepository(private val preguntaDAO: PreguntaDAO) {
    fun obtener(): LiveData<List<PreguntaEntity>> {
        return preguntaDAO.obtener()
    }
}