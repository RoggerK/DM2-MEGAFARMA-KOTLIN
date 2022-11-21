package idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo

data class LoginResponse(
    var token: String,
    var nombre: String,
    var apellido: String,
    var dni: String,
    var correo: String,
    var idcliente: Int
)
