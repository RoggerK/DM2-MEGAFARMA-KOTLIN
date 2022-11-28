package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityLibroBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dbHelper

class LibroActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityLibroBinding
    private lateinit var reclamosDBHelper: dbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reclamosDBHelper = dbHelper(this)

        binding.tvRegistrarReclamo.setOnClickListener(this)

        val token = intent
            .getSerializableExtra("token") as String

        println("token: $token")

    }

    override fun onClick(view: View?) {
        when(view?.id){
            binding.tvRegistrarReclamo.id -> registrarReclamo()
        }
    }

    fun registrarReclamo(){
        if(binding.idMotivo.text.toString().isNotBlank() &&
                binding.idDescripcion.text.toString().isNotBlank()){
            reclamosDBHelper.aniadorDatos(binding.idMotivo.text.toString(),
            binding.idDescripcion.text.toString())
            binding.idMotivo.setText("")
            binding.idDescripcion.setText("")
            binding.idMotivo.isFocusableInTouchMode
            binding.idMotivo.requestFocus()
            AppMessage.enviarMensaje(binding.root,"Se registró correctamente",TypeMessage.SUCCESSFULL)
        }else{
            AppMessage.enviarMensaje(binding.root,"No se registro correctamente," +
                    "los campos no pueden estar vacíos",TypeMessage.DANGER)
        }
    }
}