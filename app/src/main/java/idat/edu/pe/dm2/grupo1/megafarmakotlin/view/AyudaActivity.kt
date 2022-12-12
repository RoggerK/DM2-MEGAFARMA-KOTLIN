package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityAyudaBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntaResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PreguntasAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AtencionRetrofitViewModel

class AyudaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyudaBinding
    private lateinit var atencionRetrofitViewModel: AtencionRetrofitViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAyudaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        atencionRetrofitViewModel = ViewModelProvider(this)[AtencionRetrofitViewModel::class.java]
        atencionRetrofitViewModel.listarPreguntas()
        atencionRetrofitViewModel.responsePregunta.observe(this, Observer { response ->
            obtenerLista(response)
        })
    }

    private fun obtenerLista(response: List<PreguntaResponse>) {
        binding.idPreguntasRecycler.layoutManager = LinearLayoutManager(this)
        binding.idPreguntasRecycler.adapter = PreguntasAdapter(response)
    }

}