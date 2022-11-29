package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request

data class ActualizarClienteRequest(
    var correo: String,
    var celular: String,
    var contrasenia: String
)