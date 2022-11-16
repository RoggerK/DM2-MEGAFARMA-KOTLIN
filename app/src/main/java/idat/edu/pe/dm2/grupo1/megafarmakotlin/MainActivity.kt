package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMainBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityRegistrarBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.intz.UsuarioAPI
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginUsuario
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.TokenUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var urlFarma =
        "https://megafarma.herokuapp.com/"

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
            var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(urlFarma)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            var usuarioAPI: UsuarioAPI = retrofit.create(UsuarioAPI::class.java)

            var call: Call<TokenUsuario> = usuarioAPI.iniciarSesion(
                LoginUsuario(
                    binding.edLogCorreo.text.toString().trim(),
                    binding.edLogContrsenia.text.toString().trim()
                )
            )

            call.enqueue(object : Callback<TokenUsuario> {
                override fun onResponse(
                    call: Call<TokenUsuario>,
                    response: Response<TokenUsuario>
                ) {
                    var tokenUsuario: TokenUsuario = response.body()!!
                    AppMessage.enviarMensaje(
                        binding.root, "Token: ${tokenUsuario.token}",
                        TypeMessage.INFO
                    )
                }

                override fun onFailure(call: Call<TokenUsuario>, t: Throwable) {
                    println(t.message)
                    AppMessage.enviarMensaje(
                        binding.root, "Error: ${t.message}",
                        TypeMessage.DANGER
                    )
                }
            })

            limpiarCampos()
        }
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