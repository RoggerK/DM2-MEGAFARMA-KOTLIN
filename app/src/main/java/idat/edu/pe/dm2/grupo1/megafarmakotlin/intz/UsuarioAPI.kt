package idat.edu.pe.dm2.grupo1.megafarmakotlin.intz

import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.Mensaje
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.RegistrarCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioAPI {
    @POST("auth/login")
    fun iniciarSesion(@Body loginUsuario: LoginRequest): Call<LoginResponse>

    @POST("auth/registrar")
    fun registrarUsuario(@Body registrarCliente: RegistrarCliente): Call<Mensaje>
}