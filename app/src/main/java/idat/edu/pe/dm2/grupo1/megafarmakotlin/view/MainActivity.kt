package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMainBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel
    private var authEntity: AuthEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]

        authSQLiteViewModel.obtener().observe(this , Observer { response ->
            authEntity = response
        })

        Handler(Looper.getMainLooper()).postDelayed({
            if(authEntity != null) {
                startActivity(Intent(this,
                    MenuClienteActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this,
                    LoginActivity::class.java))
                finish()
            }
        }, 3000) //3s = 3000

        //animacion de 3 o 5 segundos para hacer dichas consultas y registros
        //verificar token si hay ingresa principal sino login
        //funcion que actualiza token existente
    }
}