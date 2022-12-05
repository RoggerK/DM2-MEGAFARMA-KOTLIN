package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMainBinding
//import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.AuthTableController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,
                LoginActivity::class.java))
            finish()
        }, 3000) //3s = 3000

        //animacion de 3 o 5 segundos para hacer dichas consultas y registros
        //verificar token si hay ingresa principal sino login
        //funcion que actualiza token existente
    }
}