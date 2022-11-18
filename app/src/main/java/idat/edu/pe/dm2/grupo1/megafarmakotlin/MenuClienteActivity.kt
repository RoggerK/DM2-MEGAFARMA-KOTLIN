package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityMenuClienteBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.TokenUsuario

class MenuClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        setContentView(R.layout.item_productos)
        binding = ActivityMenuClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listaToken = intent
            .getSerializableExtra("token") as ArrayList<String>
        val token = TokenUsuario(token = listaToken[0], nombre = listaToken[1],
            apellido = listaToken[2], dni = listaToken[3], correo = listaToken[4], idcliente = listaToken[5].toInt())

        println("id: ${token.idcliente}\ntoken: ${token.token}\nnombre: ${token.nombre}\napellido: ${token.apellido}\ndni: ${token.dni}\ncorreo: ${token.correo}")

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_menu_cliente)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.principalFragment, R.id.carritoFragment, R.id.usuarioFragment , R.id.libroFragment , R.id.ayudaFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}