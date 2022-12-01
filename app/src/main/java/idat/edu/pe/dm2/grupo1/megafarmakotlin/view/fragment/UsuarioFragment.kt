package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.FragmentUsuarioBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFramentUsuarioListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthViewModel
import java.util.regex.Pattern


class UsuarioFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentUsuarioBinding
    private lateinit var listernerUsuario: OnFramentUsuarioListerne
    private lateinit var authViewModel: AuthViewModel
    private var listaAgregado = ArrayList<String>()
    private var token = ""

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listernerUsuario = context as OnFramentUsuarioListerne
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar interfaz");
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsuarioBinding.inflate(inflater, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        binding.btGuardarCambios.setOnClickListener(this)
        binding.btLibroReclamacion.setOnClickListener(this)
        binding.btNecesitoAyuda.setOnClickListener(this)
        authViewModel.responseActualizar.observe(viewLifecycleOwner, Observer {
            response -> obtenerRespuestaDatos(response)
        })
        return binding.root
    }

    private fun obtenerRespuestaDatos(response: GlobalResponse) {
        if(response.respuesta) {
            AppMessage.enviarMensaje(
                binding.root, "INFO: ${response.mensaje}",
                TypeMessage.SUCCESSFULL
            )
            limpiarFormulario()
        } else {
            AppMessage.enviarMensaje(
                binding.root, "INFO: ${response.mensaje}",
                TypeMessage.INFO
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("llavePrincipal",
            this, FragmentResultListener {
                requestKey, bundle ->
                    token = bundle.getString("token") as String
                    listaAgregado = bundle.getStringArrayList("listaAgregado") as ArrayList<String>
            }
        )

        parentFragmentManager.setFragmentResultListener("llaveCarrito",
            this, FragmentResultListener {
                    requestKey, bundle ->
                token = bundle.getString("token") as String
                listaAgregado = bundle.getStringArrayList("listaCarrito") as ArrayList<String>
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        val bundle1 = Bundle()
        bundle1.putString("token", token)
        bundle1.putStringArrayList("listaCarrito", listaAgregado)
        parentFragmentManager.setFragmentResult("llaveCarrito", bundle1)

        val bundle2 = Bundle()
        bundle2.putString("token", token)
        bundle2.putStringArrayList("listaAgregado", listaAgregado)
        parentFragmentManager.setFragmentResult("llavePrincipal", bundle2)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btGuardarCambios -> guardarCambios()
            R.id.btLibroReclamacion -> listernerUsuario.onClickButtonUsuarioLibro()
            R.id.btNecesitoAyuda -> listernerUsuario.onClickButtonUsuarioAyuda()
        }
    }

    private fun guardarCambios() {
        val correo = binding.edtCorreo.text.toString()
        val celular = binding.edtCelular.text.toString()
        val contrasenia = binding.edtContrasenia.text.toString()

        if (binding.edtCorreo.text.toString() == ""
            && binding.edtCelular.text.toString() == ""
            && binding.edtContrasenia.text.toString() == ""
        ) {
            AppMessage.enviarMensaje(
                binding.root, "Ingrese dato donde desea actualizar",
                TypeMessage.INFO
            )
        } else if (validarFormulario()) {
            authViewModel.actualizarDatosUsuario(6, correo, celular, contrasenia,
                "Bearer $token")
        }
    }

    private fun validarFormulario(): Boolean {
        if (binding.edtCorreo.text.toString() != "") {
            if (!validarCorreo()) {
                return false
            } else if (binding.edtCelular.text.toString() != "") {
                if (!validarCelular()) {
                    return false
                } else if (binding.edtContrasenia.text.toString() != "") {
                    if (!validarContrasenia()) {
                        return false
                    }
                }
            }
        }

        return true
    }

    private fun limpiarFormulario() {
        binding.edtCorreo.setText("")
        binding.edtCelular.setText("")
        binding.edtContrasenia.setText("")
    }

    private fun validarCorreo(): Boolean {
        var respuesta = true
        if (!verificarFormatoCorreo()) {
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
        if (!(binding.edtCelular.text.toString().trim().startsWith("9", 0))) {
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
        if (!verificarFormatoContrasenia()) {
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
}