package idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.PreguntaEntity

@Dao
interface PreguntaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertar(vararg preguntaEntity: PreguntaEntity)

    @Query("SELECT * FROM pregunta")
    fun obtener(): LiveData<List<PreguntaEntity>>
}