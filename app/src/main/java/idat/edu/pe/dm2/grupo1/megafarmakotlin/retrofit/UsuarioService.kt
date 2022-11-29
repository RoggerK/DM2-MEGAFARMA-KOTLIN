package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ActualizarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.RegistrarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import retrofit2.Call
import retrofit2.http.*

interface UsuarioService {
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("auth/registrar")
    fun registrarCliente(@Body registrarClienteRequest: RegistrarClienteRequest): Call<GlobalResponse>

    @PUT("cliente/actualizar/{id}")
    fun actualizarDatos(@Path("id") id: Int, @Body datos: ActualizarClienteRequest,
                        @Header("Authorization") token: String) : Call<GlobalResponse>
}