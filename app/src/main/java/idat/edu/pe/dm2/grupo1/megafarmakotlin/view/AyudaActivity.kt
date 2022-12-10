package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityAyudaBinding

class AyudaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyudaBinding
    //private lateinit var MegaFarmaRoomDatabase: MegaFarmaRoomDatabase
    //private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAyudaBinding.inflate(layoutInflater)
        //MegaFarmaRoomDatabase = MegaFarmaRoomDatabase(this)
        setContentView(binding.root)

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