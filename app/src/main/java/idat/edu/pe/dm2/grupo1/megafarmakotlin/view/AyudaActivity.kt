package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import idat.edu.pe.dm2.grupo1.megafarmakotlin.PreguntasAdapter
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityAyudaBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dbHelper
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntasResponse

class AyudaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyudaBinding
    private lateinit var DBHelper: dbHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityAyudaBinding.inflate(layoutInflater)
        DBHelper = dbHelper(this)
        setContentView(binding.root)

        db = DBHelper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM preguntas" , null)

        val adapter = PreguntasAdapter()
        adapter.RecyclerViewAdapterPreguntas(this, cursor)

        binding.idPreguntasRecycler.setHasFixedSize(true)
        binding.idPreguntasRecycler.layoutManager = LinearLayoutManager(this)
        binding.idPreguntasRecycler.adapter = adapter

    }

    override fun onDestroy() {
        super.onDestroy()
        db.close()
    }
}