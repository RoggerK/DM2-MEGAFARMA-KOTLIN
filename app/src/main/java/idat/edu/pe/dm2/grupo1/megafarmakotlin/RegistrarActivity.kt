package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityRegistrarBinding

class RegistrarActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegistrar.setOnClickListener(this)
        binding.btnCancelar.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnRegistrar.id -> registrarCliente()
            binding.btnCancelar.id -> cancelarActivity()
        }
    }

    private fun cancelarActivity() {
        this.finish()
    }

    private fun registrarCliente() {
        if (validarFormulario()) {
            AppMessage.enviarMensaje(
                binding.root, "Registro con exito",
                TypeMessage.SUCCESSFULL
            )
        }
    }

    private fun validarFormulario(): Boolean {
        if (!validarNombreApellido()) {
            AppMessage.enviarMensaje(
                binding.root, "Ingrese nombre y apellido",
                TypeMessage.DANGER
            )

            return false
        } else if (!validarDNI()) {
            return false
        } else if (!validarCelular()) {
            return false
        }

        return true
    }

    private fun validarNombreApellido(): Boolean {
        var respuesta = true
        if (binding.edNombreUser.text.toString().trim().isEmpty()) {
            binding.edNombreUser.isFocusableInTouchMode = true
            binding.edNombreUser.requestFocus()
            respuesta = false
        } else if (binding.edApellido.text.toString().trim().isEmpty()) {
            binding.edApellido.isFocusableInTouchMode = true
            binding.edApellido.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarDNI(): Boolean {
        var respuesta = true
        if (binding.edDNI.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El DNI no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edDNI.isFocusableInTouchMode = true
            binding.edDNI.requestFocus()
            respuesta = false
        } else if (binding.edDNI.text.toString().trim().length != 8) {
            AppMessage.enviarMensaje(
                binding.root, "El DNI solo tiene 8 digitos",
                TypeMessage.INFO
            )
            binding.edDNI.isFocusableInTouchMode = true
            binding.edDNI.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarCelular(): Boolean {
        var respuesta = true
        if (binding.edCelular.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Celular no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edCelular.isFocusableInTouchMode = true
            binding.edCelular.requestFocus()
            respuesta = false
        } else if (binding.edCelular.text.toString().trim().length != 9) {
            AppMessage.enviarMensaje(
                binding.root, "El Celular solo tiene 9 digitos",
                TypeMessage.INFO
            )
            binding.edCelular.isFocusableInTouchMode = true
            binding.edCelular.requestFocus()
            respuesta = false
        }

        return respuesta
    }

}