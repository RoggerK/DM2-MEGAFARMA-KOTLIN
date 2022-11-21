package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMainBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.intz.UsuarioAPI
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private val urlFarma = "https://megafarma.herokuapp.com/megafarma/rest/api/v1/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogIniciarSesion.setOnClickListener(this)
        binding.btnLogRegistrarse.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnLogIniciarSesion.id -> iniciarSesion()
            binding.btnLogRegistrarse.id -> abrirRegistrarMe()
        }
    }

    private fun abrirRegistrarMe() {
        val intent = Intent(this, RegistrarActivity::class.java)
        startActivity(intent)
    }

    private fun iniciarSesion() {
        if (validarFormulario()) {
            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(urlFarma)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val usuarioAPI: UsuarioAPI = retrofit.create(UsuarioAPI::class.java)

            var call: Call<LoginResponse> = usuarioAPI.iniciarSesion(
                LoginRequest(
                    binding.edLogCorreo.text.toString().trim(),
                    binding.edLogContrsenia.text.toString().trim()
                )
            )

            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val tokenUsuario: LoginResponse = response.body()!!
                        iniciarMenuCliente(tokenUsuario)
                    } else {
                        AppMessage.enviarMensaje(
                            binding.root, "Error: usuario o contraseña",
                            TypeMessage.INFO
                        )
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    AppMessage.enviarMensaje(
                        binding.root, "Error: ${t.message}",
                        TypeMessage.DANGER
                    )
                }
            })

            limpiarCampos()
        }
    }

    private fun iniciarMenuCliente(tokenUsuario: LoginResponse) {
        val arrayToken = ArrayList<String>()
        arrayToken.add(tokenUsuario.token)
        arrayToken.add(tokenUsuario.nombre)
        arrayToken.add(tokenUsuario.apellido)
        arrayToken.add(tokenUsuario.dni)
        arrayToken.add(tokenUsuario.correo)
        arrayToken.add(tokenUsuario.idcliente.toString())

        val intentMenuClienteActivity = Intent(
            this, MenuClienteActivity::class.java
        ).apply {
            putExtra("token", arrayToken)
        }
        finalizarAtivity()
        startActivity(intentMenuClienteActivity)
    }

    private fun finalizarAtivity() {
        this.finish()
    }

    private fun limpiarCampos() {
        binding.edLogCorreo.setText("")
        binding.edLogContrsenia.setText("")
        binding.edLogCorreo.isFocusableInTouchMode = true
        binding.edLogCorreo.requestFocus()
    }

    private fun validarFormulario(): Boolean {
        if (!validarCorreo()) {
            return false
        } else if (!validarContrasenia()) {
            return false
        }

        return true
    }

    private fun validarCorreo(): Boolean {
        var respuesta = true
        if (binding.edLogCorreo.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Correo no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edLogCorreo.isFocusableInTouchMode = true
            binding.edLogCorreo.requestFocus()
            respuesta = false
        } else if (!verificarFormatoCorreo()) {
            AppMessage.enviarMensaje(
                binding.root, "El formato del Correo es invalido ",
                TypeMessage.INFO
            )
            binding.edLogCorreo.isFocusableInTouchMode = true
            binding.edLogCorreo.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun verificarFormatoCorreo(): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(binding.edLogCorreo.text.toString().trim()).matches()
    }

    private fun validarContrasenia(): Boolean {
        var respuesta = true
        if (binding.edLogContrsenia.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "La Contraseña no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edLogContrsenia.isFocusableInTouchMode = true
            binding.edLogContrsenia.requestFocus()
            respuesta = false
        }
        return respuesta
    }
}