package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.Constante
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.SharedPreferencesManager
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMainBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]

        Handler(Looper.getMainLooper()).postDelayed({
            if (verificarAccesoRapido()) {
                startActivity(Intent(this, MenuClienteActivity::class.java))
                finish()
            } else {
                SharedPreferencesManager().deletePreferences(Constante().PREF_ACCESO)
                authSQLiteViewModel.eliminarTodo()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

        }, 3000) //3s = 3000

        //animacion de 3 o 5 segundos para hacer dichas consultas y registros
        //verificar token si hay ingresa principal sino login
        //funcion que actualiza token existente
    }

    private fun verificarAccesoRapido(): Boolean {
        return SharedPreferencesManager()
            .getSomeBooleanValue(Constante().PREF_ACCESO)
    }
}