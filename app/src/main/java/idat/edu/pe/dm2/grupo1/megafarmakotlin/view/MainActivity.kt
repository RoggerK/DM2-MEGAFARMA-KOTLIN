package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMainBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.UsuarioService
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.request.LoginRequest
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnLogIniciarSesion.setOnClickListener(this)
        binding.btnLogRegistrarse.setOnClickListener(this)

        authViewModel.responseLogin.observe(this, Observer { response ->
            obtenerRespuestaLogin(response)
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnLogIniciarSesion.id -> iniciarSesion()
            binding.btnLogRegistrarse.id -> abrirRegistrarMe()
        }
    }

    private fun obtenerRespuestaLogin(response: LoginResponse?) {
        if(response != null) {
            limpiarCampos()
            iniciarMenuCliente(response)
        }

        binding.btnLogIniciarSesion.isEnabled = true
        binding.btnLogRegistrarse.isEnabled = true
    }

    private fun abrirRegistrarMe() {
        val intent = Intent(this, RegistrarActivity::class.java)
        startActivity(intent)
    }

    private fun iniciarSesion() {
        binding.btnLogIniciarSesion.isEnabled = false
        binding.btnLogRegistrarse.isEnabled = false
        if (validarFormulario()) {
            authViewModel.inciarSesion(binding.edLogCorreo.text.toString(),
                binding.edLogContrsenia.text.toString(), binding.root)
        } else {
            binding.btnLogIniciarSesion.isEnabled = true
            binding.btnLogRegistrarse.isEnabled = true
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

        finish()

        startActivity(intentMenuClienteActivity)
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