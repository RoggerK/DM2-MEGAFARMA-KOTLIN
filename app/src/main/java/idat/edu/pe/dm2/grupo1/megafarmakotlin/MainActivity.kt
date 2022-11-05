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

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogIniciarSesion.setOnClickListener(this)
        binding.btnLogRegistrarse.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when(view.id) {
            binding.btnLogIniciarSesion.id -> iniciarSesion()
            binding.btnLogRegistrarse.id -> abrirRegistrarMe()
        }
    }

    private fun abrirRegistrarMe() {
        val intent = Intent(this, RegistrarActivity::class.java)
        startActivity(intent)
    }

    private fun iniciarSesion() {
        if(validarFormulario()) {
            AppMessage.enviarMensaje(
                binding.root, "Iniciaste sesion",
                TypeMessage.SUCCESSFULL
            )
        }
    }

    private fun validarFormulario(): Boolean {
        if(!validarCorreo()) {
            return false
        } else if(!validarContrasenia()) {
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

        return  respuesta
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