package idat.edu.pe.dm2.grupo1.megafarmakotlin.db.model

data class AuthTable(
    var id: Int,
    var token: String,
    var nombre: String,
    var apellido: String,
    var dni: String,
    var correo: String,
    var idcliente: Int
)
