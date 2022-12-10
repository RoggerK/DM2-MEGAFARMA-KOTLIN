package idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pregunta")
data class PreguntaEntity(
    @PrimaryKey
    var idPreguntas: Int,
    var titulo: String,
    var descripcion: String
)