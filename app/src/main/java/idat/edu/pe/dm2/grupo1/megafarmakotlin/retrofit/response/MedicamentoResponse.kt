package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response

data class MedicamentoResponse(
    var idproducto: Int,
    var imagen_producto: String,
    var nombre_producto: String,
    var presentacion: String,
    var precio_unitario: Double,
    var precio_total: Double,
    var pedido: Int = 1
)
