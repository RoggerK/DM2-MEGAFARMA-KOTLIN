package idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo

data class RegistrarCliente(
    var nombre: String,
    var apellido: String,
    var celular: String,
    var dni: String,
    var fc_nac: String,
    var correo: String,
    var contrasenia: String,
    var terminos: Boolean,
    var activo: Boolean
)
