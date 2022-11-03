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

    }

    override fun onClick(view: View) {
        when(view.id) {
            binding.btnRegistrar.id -> registrarCliente()
        }
    }

    private fun registrarCliente() {
        if(validarFormulario()) {
            AppMessage.enviarMensaje(binding.root, "Registro con exito",
                TypeMessage.SUCCESSFULL)
        }
    }

    private fun validarFormulario(): Boolean {
        var respuesta = false
        if (!validarNombreApellido()) {
            AppMessage.enviarMensaje(binding.root, "Ingrese nombre y apellido",
                TypeMessage.DANGER)
        } else {
            respuesta = true
        }

        return respuesta
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
}