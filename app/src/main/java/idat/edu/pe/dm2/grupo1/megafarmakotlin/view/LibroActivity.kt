package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.AppMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.TypeMessage
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityLibroBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.dbHelper

class LibroActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityLibroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent
            .getSerializableExtra("token") as String

        println("token: $token")

    }

}