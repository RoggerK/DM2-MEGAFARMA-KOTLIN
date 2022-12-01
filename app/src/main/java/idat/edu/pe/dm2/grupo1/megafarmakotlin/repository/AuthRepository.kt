package idat.edu.pe.dm2.grupo1.megafarmakotlin.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.MegaFarmaCliente
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.ActualizarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.RegistrarClienteRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {
    var loginResponse = MutableLiveData<LoginResponse>()
    var registroRespose = MutableLiveData<GlobalResponse>()
    val actualizarReponse = MutableLiveData<GlobalResponse>()

    fun autenticarUsuario(loginRequest: LoginRequest, view: View)
            : MutableLiveData<LoginResponse> {
        val call: Call<LoginResponse> =
            MegaFarmaCliente.retrofitUsuarioService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    loginResponse.value = response.body()
                } else {
                    AppMessage.enviarMensaje(
                        view, "INFO: usuario y/o contraseña incorrecto",
                        TypeMessage.INFO
                    )
                    loginResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("ErrorIniciarSesion", t.message.toString())
            }
        })
        return loginResponse
    }

    fun registrarUsuario(registrarClienteRequest: RegistrarClienteRequest)
            : MutableLiveData<GlobalResponse> {
        val call: Call<GlobalResponse> =
            MegaFarmaCliente.retrofitUsuarioService.registrarCliente(registrarClienteRequest)
        call.enqueue(object : Callback<GlobalResponse> {
            override fun onResponse(
                call: Call<GlobalResponse>,
                response: Response<GlobalResponse>
            ) {
                registroRespose.value = response.body()
            }

            override fun onFailure(call: Call<GlobalResponse>, t: Throwable) {
                Log.e("ErrorRegistrar", t.message.toString())
            }
        })
        return registroRespose
    }

    fun actualizarDatosUsuario(id: Int, actualizarClienteRequest: ActualizarClienteRequest,
                               token: String): MutableLiveData<GlobalResponse> {
        val call: Call<GlobalResponse> =
            MegaFarmaCliente.retrofitUsuarioService.actualizarDatos(id, actualizarClienteRequest, token)
        call.enqueue(object : Callback<GlobalResponse> {
            override fun onResponse(
                call: Call<GlobalResponse>,
                response: Response<GlobalResponse>
            ) {
                actualizarReponse.value = response.body()
            }

            override fun onFailure(call: Call<GlobalResponse>, t: Throwable) {
                Log.e("ErrorDatos", t.message.toString())
            }
        })
        return actualizarReponse
    }
}