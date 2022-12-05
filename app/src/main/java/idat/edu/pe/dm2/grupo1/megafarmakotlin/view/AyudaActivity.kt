package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter.PreguntasAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityAyudaBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.MegaFarmaRoomDatabase

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