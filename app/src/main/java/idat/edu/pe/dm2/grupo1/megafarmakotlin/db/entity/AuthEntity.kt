package idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth")
data class AuthEntity(
    @PrimaryKey
    var idcliente: Int,
    var token: String,
    var nombre: String,
    var apellido: String,
    var dni: String,
    var correo: String,
)
