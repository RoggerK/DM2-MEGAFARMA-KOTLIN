package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityLibroBinding
//import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.AuthTableController

class LibroActivity : AppCompatActivity()  {
    private lateinit var binding: ActivityLibroBinding
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtenerToken()
        //println(token)
    }

    /*private fun obtenerToken() {
        val db = AuthTableController(MyApplication.instance)
        val auth = db.getAuth()
        token = auth.token
    }*/
}