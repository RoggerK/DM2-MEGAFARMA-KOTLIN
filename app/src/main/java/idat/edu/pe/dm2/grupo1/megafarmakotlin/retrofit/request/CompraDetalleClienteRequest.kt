package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request

data class CompraDetalleClienteRequest(
    var compraDTO: CompraClienteRequest,
    var listaDetalleDto: ArrayList<DetalleClienteRequest>
)