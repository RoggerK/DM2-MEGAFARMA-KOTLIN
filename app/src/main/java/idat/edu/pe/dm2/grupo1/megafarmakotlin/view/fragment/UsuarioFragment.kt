package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFramentUsuarioListerne
import java.util.regex.Pattern


class UsuarioFragment : Fragment(R.layout.fragment_usuario), View.OnClickListener {

    private lateinit var listernerUsuario: OnFramentUsuarioListerne
    private lateinit var edCorreo: EditText
    private lateinit var edCelular: EditText
    private lateinit var edContrasenia: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuario, container, false)
        edCorreo = view.findViewById(R.id.edtCorreo)
        edCelular = view.findViewById(R.id.edtCelular)
        edContrasenia = view.findViewById(R.id.edtContrasenia)

        val buttonGuardar = view.findViewById<Button>(R.id.btGuardarCambios)
        val buttonLibro = view.findViewById<Button>(R.id.btLibroReclamacion)
        val buttonAyuda = view.findViewById<Button>(R.id.btNecesitoAyuda)

        buttonGuardar.setOnClickListener(this)
        buttonLibro.setOnClickListener(this)
        buttonAyuda.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btGuardarCambios -> guardarCambios()
            R.id.btLibroReclamacion -> listernerUsuario.onClickButtonUsuarioLibro()
            R.id.btNecesitoAyuda -> listernerUsuario.onClickButtonUsuarioAyuda()
        }
    }

    private fun guardarCambios() {
        if (validarFormulario()) {
            //llamar registro
        }
    }

    private fun validarFormulario(): Boolean {
        if (!validarCorreo()) {
            return false
        } else if (!validarCelular()) {
            return false
        } else if (!validarContrasenia()) {
            return false
        }

        return true
    }

    private fun validarCorreo(): Boolean {
        var respuesta = true
        if (edCorreo.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                requireView(), "El Correo no puede estar vacio",
                TypeMessage.DANGER
            )
            edCorreo.isFocusableInTouchMode = true
            edCorreo.requestFocus()
            respuesta = false
        } else if (!verificarFormatoCorreo()) {
            AppMessage.enviarMensaje(
                requireView(), "El formato del Correo es invalido ",
                TypeMessage.INFO
            )
            edCorreo.isFocusableInTouchMode = true
            edCorreo.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun verificarFormatoCorreo(): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(edCorreo.text.toString().trim()).matches()
    }

    private fun validarCelular(): Boolean {
        var respuesta = true
        if (edCelular.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                requireView(), "El Celular no puede estar vacio",
                TypeMessage.DANGER
            )
            edCelular.isFocusableInTouchMode = true
            edCelular.requestFocus()
            respuesta = false
        } else if (!(edCelular.text.toString().trim().startsWith("9", 0))) {
            AppMessage.enviarMensaje(
                requireView(), "El Celular debe empezar con 9",
                TypeMessage.INFO
            )
            edCelular.isFocusableInTouchMode = true
            edCelular.requestFocus()
            respuesta = false
        } else if (edCelular.text.toString().trim().length != 9) {
            AppMessage.enviarMensaje(
                requireView(), "El Celular solo acepta 9 digitos",
                TypeMessage.INFO
            )
            edCelular.isFocusableInTouchMode = true
            edCelular.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarContrasenia(): Boolean {
        var respuesta = true
        if (edContrasenia.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                requireView(), "La Contraseña no puede estar vacio",
                TypeMessage.DANGER
            )
            edContrasenia.isFocusableInTouchMode = true
            edContrasenia.requestFocus()
            respuesta = false
        } else if (!verificarFormatoContrasenia()) {
            AppMessage.enviarMensaje(
                requireView(), "La Contraseña es débil. Debe tener: a-Z 0-9 @#%&+=.",
                TypeMessage.INFO
            )
            edContrasenia.isFocusableInTouchMode = true
            edContrasenia.requestFocus()
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

        return pattern.matcher(edContrasenia.text.toString().trim()).matches()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listernerUsuario = context as OnFramentUsuarioListerne
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar interfaz");
        }
    }
}