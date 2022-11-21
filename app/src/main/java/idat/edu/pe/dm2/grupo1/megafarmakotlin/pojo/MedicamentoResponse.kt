package idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo

data class MedicamentoResponse(
    var idproducto: Int,
    var imagen_producto: String,
    var nombre_producto: String,
    var presentacion: String,
    var precio_unitario: Double
)
