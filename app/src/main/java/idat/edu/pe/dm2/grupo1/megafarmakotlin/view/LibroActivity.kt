package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityLibroBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.GlobalResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AtencionRetrofitViewModel

class LibroActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLibroBinding
    private lateinit var atencionRetrofitViewModel: AtencionRetrofitViewModel
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel
    private lateinit var authEntity: AuthEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btRegistrarReclamo.setOnClickListener(this)
        binding.btSalir.setOnClickListener(this)

        atencionRetrofitViewModel = ViewModelProvider(this)[AtencionRetrofitViewModel::class.java]
        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]

        authSQLiteViewModel.obtener().observe(this, Observer { response ->
            authEntity = response
        })

        atencionRetrofitViewModel.responseReclamo.observe(this, Observer { response ->
            obtenerRepuestaReclamo(response)
        })
    }

    private fun obtenerRepuestaReclamo(response: GlobalResponse?) {
        if (response != null) {
            if (response.respuesta) {
                AppMessage.enviarMensaje(
                    binding.root,
                    "INFO: ${response.mensaje}",
                    TypeMessage.SUCCESSFULL
                )
                limpiarCampos()
            }
        } else {
            AppMessage.enviarMensaje(
                binding.root,
                "DANGER: No se pudo registrar su reclamo",
                TypeMessage.DANGER
            )
        }
    }

    private fun limpiarCampos() {
        binding.edtAsunto.setText("")
        binding.edtDescripcion.setText("")
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.btRegistrarReclamo.id -> registrarReclamo()
            binding.btSalir.id -> finish()
        }
    }

    private fun registrarReclamo() {
        if (validarFormulario()) {
            atencionRetrofitViewModel.registrarReclamo(
                binding.edtAsunto.text.toString().trim(),
                binding.edtDescripcion.text.toString().trim(),
                authEntity.idcliente,
                "Bearer ${authEntity.token}"
            )
        }
    }

    private fun validarFormulario(): Boolean {
        if (!validarAsunto()) {
            return false
        } else if (!validarDescripcion()) {
            return false
        }

        return true
    }

    private fun validarAsunto(): Boolean {
        var respuesta = true
        if (binding.edtAsunto.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "El Asunto no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edtAsunto.isFocusableInTouchMode = true
            binding.edtAsunto.requestFocus()
            respuesta = false
        }

        return respuesta
    }

    private fun validarDescripcion(): Boolean {
        var respuesta = true
        if (binding.edtDescripcion.text.toString().trim().isEmpty()) {
            AppMessage.enviarMensaje(
                binding.root, "La Descripcion no puede estar vacio",
                TypeMessage.DANGER
            )
            binding.edtDescripcion.isFocusableInTouchMode = true
            binding.edtDescripcion.requestFocus()
            respuesta = false
        }

        return respuesta
    }


}