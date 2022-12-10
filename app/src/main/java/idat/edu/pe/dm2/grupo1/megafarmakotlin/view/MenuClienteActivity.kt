package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMenuClienteBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.db.entity.AuthEntity
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFragmentCarritoListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFragmentUsuarioListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment.PrincipalFragment
import idat.edu.pe.dm2.grupo1.megafarmakotlin.viewmodel.AuthSQLiteViewModel

class MenuClienteActivity : AppCompatActivity(),
    OnFragmentUsuarioListerne, OnFragmentCarritoListerne {

    private lateinit var binding: ActivityMenuClienteBinding
    private lateinit var authSQLiteViewModel: AuthSQLiteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityMenuClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authSQLiteViewModel = ViewModelProvider(this)[AuthSQLiteViewModel::class.java]
        authSQLiteViewModel.obtener().observe(this, Observer { response ->
            enviarDatosFragmentPrincipal(response)
        })

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
    }

    private fun enviarDatosFragmentPrincipal(response: AuthEntity?) {
        if (response != null) {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_menu_cliente) as NavHostFragment
            val fragment = navHostFragment.childFragmentManager
                .findFragmentById(R.id.nav_host_fragment_activity_menu_cliente) as PrincipalFragment
            fragment.token = response.token
            fragment.llenarlistaMedicamentos()
        }
    }

    override fun abrirActivityPedido(listaCarrito: ArrayList<String>) {
        startActivity(Intent(this, PedidoActivity::class.java).apply {
            putExtra("listaCarrito", listaCarrito)
        })
    }

    override fun onClickButtonCerrarSesion() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onClickButtonUsuarioLibro() {
        cargarActivityLibro()
    }

    override fun onClickButtonUsuarioAyuda() {
        cargarActivityAyuda()
    }

    private fun cargarActivityLibro() {
        startActivity(Intent(this, LibroActivity::class.java))
    }

    private fun cargarActivityAyuda() {
        startActivity(Intent(this, AyudaActivity::class.java))
    }

}