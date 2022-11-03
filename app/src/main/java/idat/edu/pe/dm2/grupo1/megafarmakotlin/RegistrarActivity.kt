package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityRegistrarBinding
import java.util.regex.Pattern

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
        if (!validarNombre()) {
            return false
        } else if (!validarApellido()) {
            return false
        } else if (!validarDNI()) {
            return false
        } else if (!validarCelular()) {
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
        } else if(!verificarFormatoContrasenia()) {
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