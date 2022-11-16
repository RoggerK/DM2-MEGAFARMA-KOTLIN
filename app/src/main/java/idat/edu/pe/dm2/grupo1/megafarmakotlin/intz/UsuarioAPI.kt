package idat.edu.pe.dm2.grupo1.megafarmakotlin.intz

import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginUsuario
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.TokenUsuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioAPI {
    @POST("megafarma/rest/api/v1/auth/login")
    fun iniciarSesion(@Body loginUsuario: LoginUsuario): Call<TokenUsuario>
}