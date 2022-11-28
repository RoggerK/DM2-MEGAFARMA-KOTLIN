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
import androidx.fragment.app.FragmentResultListener
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentCarritoBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentUsuarioBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFramentUsuarioListerne
import java.util.regex.Pattern


class UsuarioFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUsuarioBinding
    private lateinit var listernerUsuario: OnFramentUsuarioListerne
    private var listaAgregado = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false)

        binding.btGuardarCambios.setOnClickListener(this)
        binding.btLibroReclamacion.setOnClickListener(this)
        binding.btNecesitoAyuda.setOnClickListener(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("llaveCarrito",
            this, FragmentResultListener { requestKey, bundle ->
                listaAgregado = bundle.getStringArrayList("listaCarrito") as ArrayList<String>
                bundle.putStringArrayList("listaAgregado", listaAgregado)
                parentFragmentManager.setFragmentResult("llavePrincipal", bundle)
            })
    }

    /*override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val bundle = Bundle()
        bundle.putStringArrayList("listaAgregado", listaAgregado)
        parentFragmentManager.setFragmentResult("llavePrincipal", bundle)
    }
*/
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
        if (binding.edtCorreo.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                requireView(), "El Correo no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edtCorreo.isFocusableInTouchMode = true
            binding.edtCorreo.requestFocus()
            respuesta = false
        } else if (!verificarFormatoCorreo()) {
            AppMessage.enviarMensaje(
                requireView(), "El formato del Correo es invalido ",
                TypeMessage.INFO
            )
            binding.edtCorreo.isFocusableInTouchMode = true
            binding.edtCorreo.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun verificarFormatoCorreo(): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(binding.edtCorreo.text.toString().trim()).matches()
    }

    private fun validarCelular(): Boolean {
        var respuesta = true
        if (binding.edtCelular.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                requireView(), "El Celular no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edtCelular.isFocusableInTouchMode = true
            binding.edtCelular.requestFocus()
            respuesta = false
        } else if (!(binding.edtCelular.text.toString().trim().startsWith("9", 0))) {
            AppMessage.enviarMensaje(
                requireView(), "El Celular debe empezar con 9",
                TypeMessage.INFO
            )
            binding.edtCelular.isFocusableInTouchMode = true
            binding.edtCelular.requestFocus()
            respuesta = false
        } else if (binding.edtCelular.text.toString().trim().length != 9) {
            AppMessage.enviarMensaje(
                requireView(), "El Celular solo acepta 9 digitos",
                TypeMessage.INFO
            )
            binding.edtCelular.isFocusableInTouchMode = true
            binding.edtCelular.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarContrasenia(): Boolean {
        var respuesta = true
        if (binding.edtContrasenia.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                requireView(), "La Contraseña no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edtContrasenia.isFocusableInTouchMode = true
            binding.edtContrasenia.requestFocus()
            respuesta = false
        } else if (!verificarFormatoContrasenia()) {
            AppMessage.enviarMensaje(
                requireView(), "La Contraseña es débil. Debe tener: a-Z 0-9 @#%&+=.",
                TypeMessage.INFO
            )
            binding.edtContrasenia.isFocusableInTouchMode = true
            binding.edtContrasenia.requestFocus()
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

        return pattern.matcher(binding.edtContrasenia.text.toString().trim()).matches()
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