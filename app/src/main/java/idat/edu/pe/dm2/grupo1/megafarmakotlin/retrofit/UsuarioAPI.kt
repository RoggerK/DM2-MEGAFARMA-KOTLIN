package idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit

import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.RegistrarClienteResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.RegistrarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioAPI {
    @POST("auth/login")
    fun iniciarSesion(@Body loginUsuario: LoginRequest): Call<LoginResponse>

    @POST("auth/registrar")
    fun registrarUsuario(@Body registrarClienteRequest: RegistrarClienteRequest): Call<RegistrarClienteResponse>
}