package idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.repository.AuthRetrofitRepository
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ActualizarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.RegistrarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse

class AuthRetrofitViewModel: ViewModel() {

    var responseLogin: LiveData<LoginResponse>
    var responseRegistro: LiveData<GlobalResponse>
    var responseActualizar: LiveData<GlobalResponse>
    private var repository = AuthRetrofitRepository()

    init {
        responseLogin = repository.loginResponse
        responseRegistro = repository.registroRespose
        responseActualizar = repository.actualizarReponse
    }

    fun inciarSesion(correo: String, contrasenia: String, view: View) {
        responseLogin = repository.autenticarUsuario(
            LoginRequest(correo, contrasenia), view
        )
    }

    fun registroUsuario(nombres: String, apellidos: String, celular: String, dni: String,
                        fecha: String, correo: String, contrasenia: String, terminos: Boolean,
                        activo: Boolean) {
        responseRegistro = repository.registrarUsuario(
            RegistrarClienteRequest(nombres, apellidos, celular, dni, fecha, correo, contrasenia,
                terminos, activo)
        )
    }

    fun actualizarDatosUsuario(id: Int, correo: String, celular: String, contrasenia: String,
                               token: String) {
        responseActualizar = repository.actualizarDatosUsuario(
            id, ActualizarClienteRequest(correo, celular, contrasenia), token
        )
    }
}