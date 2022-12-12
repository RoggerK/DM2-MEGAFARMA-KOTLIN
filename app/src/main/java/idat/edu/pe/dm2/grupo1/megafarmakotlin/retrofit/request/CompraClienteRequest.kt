package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request

data class CompraClienteRequest(
    var geolocalizacion: String,
    var importe: Double,
    var igv: Double,
    var total: Double,
    var idcliente: Int
)