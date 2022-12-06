package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityAyudaBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PreguntasAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.PreguntaSQLiteViewModel

class AyudaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyudaBinding
    private lateinit var preguntaSQLiteViewModel: PreguntaSQLiteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAyudaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.idPreguntasRecycler.layoutManager = LinearLayoutManager(this)

        preguntaSQLiteViewModel = ViewModelProvider(this)[PreguntaSQLiteViewModel::class.java]
        preguntaSQLiteViewModel.obtener().observe(this, Observer { response ->
            binding.idPreguntasRecycler.adapter = PreguntasAdapter(response)
        })


        //MegaFarmaRoomDatabase = MegaFarmaRoomDatabase(this)
        /*db = MegaFarmaRoomDatabase.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM preguntas" , null)

        val adapter = PreguntasAdapter()
        adapter.RecyclerViewAdapterPreguntas(this, cursor)

        binding.idPreguntasRecycler.setHasFixedSize(true)
        binding.idPreguntasRecycler.layoutManager = LinearLayoutManager(this)
        binding.idPreguntasRecycler.adapter = adapter
        */
    }

    /*override fun onDestroy() {
        super.onDestroy()
        db.close()
    }*/
}