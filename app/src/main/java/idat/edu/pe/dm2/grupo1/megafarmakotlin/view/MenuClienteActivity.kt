package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.common.MyApplication
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMenuClienteBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.AuthTableController
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFramentUsuarioListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.LoginResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment.PrincipalFragment

class MenuClienteActivity : AppCompatActivity(),
    OnFramentUsuarioListerne {

    private lateinit var binding: ActivityMenuClienteBinding
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityMenuClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_menu_cliente)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.principalFragment,
                R.id.carritoFragment,
                R.id.usuarioFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        enviarDatosFragmentPrincipal()
    }

    private fun enviarDatosFragmentPrincipal() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_menu_cliente) as NavHostFragment
        val fragment = navHostFragment.childFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_menu_cliente) as PrincipalFragment
        fragment.llenarlistaMedicamentos()
    }

    override fun onClickButtonUsuarioLibro() {
        cargarActivityLibro()
    }

    override fun onClickButtonUsuarioAyuda() {
        cargarActivityAyuda()
    }

    private fun cargarActivityLibro() {
        val intent = Intent(this, LibroActivity::class.java)
        startActivity(intent)
    }

    private fun cargarActivityAyuda() {
        val intent = Intent(this, AyudaActivity::class.java)
        startActivity(intent)
    }

}