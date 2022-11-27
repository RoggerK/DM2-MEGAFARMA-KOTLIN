package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityRegistrarBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthViewModel
import java.time.LocalDate
import java.time.Period
import java.util.regex.Pattern

class RegistrarActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegistrarBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnRegistrar.setOnClickListener(this)
        binding.btnCancelar.setOnClickListener(this)

        authViewModel.responseRegistro.observe(this, Observer { response ->
            obtenerRespuestaRegistrar(response)
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btnRegistrar.id -> registrarCliente()
            binding.btnCancelar.id -> finish()
        }
    }

    private fun obtenerRespuestaRegistrar(response: GlobalResponse?) {
        if(response != null) {
            AppMessage.enviarMensaje(binding.root, "INFO: ${response.mensaje}", TypeMessage.SUCCESSFULL)
            limpiarCampos()
        } else {
            AppMessage.enviarMensaje(binding.root, "INFO: DNI y/o correo existe", TypeMessage.INFO)
        }
        binding.btnCancelar.isEnabled = true
        binding.btnRegistrar.isEnabled = true
    }

    private fun registrarCliente() {
        binding.btnCancelar.isEnabled = false
        binding.btnRegistrar.isEnabled = false

        if (validarFormulario()) {
            val fechaNacimiento = binding.edAnio.text.toString().trim() + "-" +
                    binding.edMes.text.toString().trim() + "-" +
                    binding.edDia.text.toString().trim() + "-"

            authViewModel.registroUsuario(binding.edNombreUser.text.toString().trim(),
                binding.edApellido.text.toString().trim(),
                binding.edCelular.text.toString().trim(),
                binding.edDNI.text.toString().trim(),
                fechaNacimiento,
                binding.edEmailUser.text.toString().trim(),
                binding.edPasswordUser.text.toString().trim(),
                true,
                true)
        } else {
            binding.btnCancelar.isEnabled = true
            binding.btnRegistrar.isEnabled = true
        }
    }

    private fun limpiarCampos() {
        binding.edNombreUser.setText("")
        binding.edApellido.setText("")
        binding.edDNI.setText("")
        binding.edCelular.setText("")
        binding.edDia.setText("")
        binding.edMes.setText("")
        binding.edAnio.setText("")
        binding.edEmailUser.setText("")
        binding.edPasswordUser.setText("")

        binding.edNombreUser.isFocusableInTouchMode = true
        binding.edNombreUser.requestFocus()
    }

    private fun validarFormulario(): Boolean {
        if (!validarNombre()) {
            return false
        } else if (!validarApellido()) {
            return false
        } else if (!validarDNI()) {
            return false
        } else if (!validarCelular()) {
            return false
        } else if (!validarFechaNacimiento()) {
            return false
        } else if (!validarCorreo()) {
            return false
        } else if (!validarContrasenia()) {
            return false
        }

        return true
    }

    private fun validarNombre(): Boolean {
        var respuesta = true
        if (binding.edNombreUser.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Nombre no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edNombreUser.isFocusableInTouchMode = true
            binding.edNombreUser.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarApellido(): Boolean {
        var respuesta = true
        if (binding.edApellido.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Apellido no puede estar vacio",
                TypeMessage.DANGER
            )
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
                binding.root, "El DNI solo acepta 8 digitos",
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
        } else if(!(binding.edCelular.text.toString().trim().startsWith("9", 0))) {
            AppMessage.enviarMensaje(
                binding.root, "El Celular debe empezar con 9",
                TypeMessage.INFO
            )
            binding.edCelular.isFocusableInTouchMode = true
            binding.edCelular.requestFocus()
            respuesta = false
        } else if (binding.edCelular.text.toString().trim().length != 9) {
            AppMessage.enviarMensaje(
                binding.root, "El Celular solo acepta 9 digitos",
                TypeMessage.INFO
            )
            binding.edCelular.isFocusableInTouchMode = true
            binding.edCelular.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarFechaNacimiento(): Boolean {
        var respuesta = true
        if (!validarDia()) {
            respuesta = false
        } else if (!validarMes()) {
            respuesta = false
        } else if (!validarAnio()) {
            respuesta = false
        } else if (!validarEdad()) {
            respuesta = false
        }

        return respuesta
    }

    private fun validarEdad(): Boolean {
        var respuesta = true
        val edad = Period.between(
            LocalDate.of(
                binding.edAnio.text.toString().trim().toInt(),
                binding.edMes.text.toString().trim().toInt(),
                binding.edDia.text.toString().trim().toInt()
            ),
            LocalDate.now()
        )
        if (edad.years < 18) {
            AppMessage.enviarMensaje(
                binding.root, "Debe ser mayor de edad para crear una cuenta",
                TypeMessage.INFO
            )
            binding.edAnio.isFocusableInTouchMode = true
            binding.edAnio.requestFocus()
            respuesta = false
        }
        return respuesta
    }

    private fun validarDia(): Boolean {
        var respuesta = true
        if (binding.edDia.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Día no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edDia.isFocusableInTouchMode = true
            binding.edDia.requestFocus()
            respuesta = false
        } else if (binding.edDia.text.toString().trim().toInt() !in 1..31) {
            AppMessage.enviarMensaje(
                binding.root, "El Día inicia des del 1 al 31",
                TypeMessage.INFO
            )
            binding.edDia.isFocusableInTouchMode = true
            binding.edDia.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarMes(): Boolean {
        var respuesta = true
        if (binding.edMes.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Mes no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edMes.isFocusableInTouchMode = true
            binding.edMes.requestFocus()
            respuesta = false
        } else if (binding.edMes.text.toString().trim().toInt() !in 1..12) {
            AppMessage.enviarMensaje(
                binding.root, "El Año solo tiene 12 meses",
                TypeMessage.INFO
            )
            binding.edMes.isFocusableInTouchMode = true
            binding.edMes.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarAnio(): Boolean {
        var respuesta = true
        if (binding.edAnio.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Año no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edAnio.isFocusableInTouchMode = true
            binding.edAnio.requestFocus()
            respuesta = false
        } else if (binding.edAnio.text.toString().trim().length != 4) {
            AppMessage.enviarMensaje(
                binding.root, "El Año debe tener 4 digitos",
                TypeMessage.INFO
            )
            binding.edAnio.isFocusableInTouchMode = true
            binding.edAnio.requestFocus()
            respuesta = false
        } else if(binding.edAnio.text.toString().trim().toInt() < 1900) {
            AppMessage.enviarMensaje(
                binding.root, "El Año aceptable es apartir de 1900 a más",
                TypeMessage.INFO
            )
            binding.edAnio.isFocusableInTouchMode = true
            binding.edAnio.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarCorreo(): Boolean {
        var respuesta = true
        if (binding.edEmailUser.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Correo no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edEmailUser.isFocusableInTouchMode = true
            binding.edEmailUser.requestFocus()
            respuesta = false
        } else if (!verificarFormatoCorreo()) {
            AppMessage.enviarMensaje(
                binding.root, "El formato del Correo es invalido ",
                TypeMessage.INFO
            )
            binding.edEmailUser.isFocusableInTouchMode = true
            binding.edEmailUser.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun verificarFormatoCorreo(): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(binding.edEmailUser.text.toString().trim()).matches()
    }

    private fun validarContrasenia(): Boolean {
        var respuesta = true
        if (binding.edPasswordUser.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "La Contraseña no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edPasswordUser.isFocusableInTouchMode = true
            binding.edPasswordUser.requestFocus()
            respuesta = false
        } else if (!verificarFormatoContrasenia()) {
            AppMessage.enviarMensaje(
                binding.root, "La Contraseña es débil. Debe tener: a-Z 0-9 @#%&+=.",
                TypeMessage.INFO
            )
            binding.edPasswordUser.isFocusableInTouchMode = true
            binding.edPasswordUser.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun verificarFormatoContrasenia(): Boolean {
        val pattern = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +          //al menos un numero
                    "(?=.*[a-z])" +          //al menos una letra minusucla
                    "(?=.*[A-Z])" +          //al menos una letra mayuscula
                    "(?=.*[@#$%^&+=.])" +    //algunos de ellos debe estar
                    "(?=\\S+$)" +            //nada de espacios
                    ".{4,}" +                //al menos 4 caracteres
                    "$"
        )

        return pattern.matcher(binding.edPasswordUser.text.toString().trim()).matches()
    }
}