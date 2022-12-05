package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityLoginBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthRetrofitViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRetrofitViewModel: AuthRetrofitViewModel
    private lateinit var authSQLiteRepository: AuthSQLiteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authRetrofitViewModel = ViewModelProvider(this)[AuthRetrofitViewModel::class.java]
        authSQLiteRepository = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]

        binding.btnLogIniciarSesion.setOnClickListener(this)
        binding.btnLogRegistrarse.setOnClickListener(this)

        authRetrofitViewModel.responseLogin.observe(this, Observer { response ->
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
            guardarAuthSQLite(response)
            iniciarMenuCliente()
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
            authRetrofitViewModel.inciarSesion(binding.edLogCorreo.text.toString(),
                binding.edLogContrsenia.text.toString(), binding.root)
        } else {
            binding.btnLogIniciarSesion.isEnabled = true
            binding.btnLogRegistrarse.isEnabled = true
        }
    }

    private fun guardarAuthSQLite(auth: LoginResponse) {
        val authEntity = AuthEntity(1, auth.token, auth.nombre, auth.apellido, auth.dni, auth.correo,
            auth.idcliente)
        authSQLiteRepository.insertar(authEntity)
    }

    private fun iniciarMenuCliente() {
        startActivity(Intent(this, MenuClienteActivity::class.java))
        finish()
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