package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request

data class DetalleClienteRequest(
    var cantidad: Int,
    var importe: Double,
    var igv: Double,
    var total: Double,
    var idproducto: Int
)
