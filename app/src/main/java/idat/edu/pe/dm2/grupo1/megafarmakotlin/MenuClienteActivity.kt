package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMenuClienteBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFramentUsuarioListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginResponse

class MenuClienteActivity : AppCompatActivity(),
    OnFramentUsuarioListerne {

    private lateinit var binding: ActivityMenuClienteBinding
    private lateinit var token: LoginResponse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityMenuClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listaToken = intent
            .getSerializableExtra("token") as ArrayList<String>
        token = LoginResponse(
            token = listaToken[0],
            nombre = listaToken[1],
            apellido = listaToken[2],
            dni = listaToken[3],
            correo = listaToken[4],
            idcliente = listaToken[5].toInt()
        )

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

    override fun onStart() {
        super.onStart()
        enviarDatosFragmentPrincipal()
    }

    private fun enviarDatosFragmentPrincipal() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_menu_cliente) as NavHostFragment
        val fragment = navHostFragment.childFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_menu_cliente) as PrincipalFragment
        fragment.token = token.token
        fragment.llenarlistaMedicamentos()
    }

    override fun onClickButtonGuardarCambios() {

    }

    override fun onClickButtonUsuarioLibro() {
        cargarActivityLibro()
    }

    override fun onClickButtonUsuarioAyuda() {
        cargarActivityAyuda()
    }

    private fun cargarActivityLibro() {
        val intent = Intent(this, LibroActivity::class.java).apply {
            putExtra("token", token.token)
        }
        startActivity(intent)
    }

    private fun cargarActivityAyuda() {
        val intent = Intent(this, AyudaActivity::class.java)
        startActivity(intent)
    }

}