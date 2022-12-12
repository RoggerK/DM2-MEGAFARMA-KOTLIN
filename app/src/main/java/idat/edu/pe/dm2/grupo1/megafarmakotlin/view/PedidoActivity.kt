package idat.edu.pe.dm2.grupo1.megafarmakotlin.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityPedidoBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFragmentPedidoListerne
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.MedicamentoResponse
import idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment.PedidoFragment

class PedidoActivity : AppCompatActivity(), OnFragmentPedidoListerne {

    private lateinit var binding: ActivityPedidoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_pedido)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.pedidoFragment, R.id.mapsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val lista = intent.getSerializableExtra("listaCarrito") as ArrayList<String>
        val listaMedicamento = realizarTransStringListMedicamento(lista)
        enviarDatosFragmentPedido(listaMedicamento)
    }

    private fun realizarTransStringListMedicamento(lista: ArrayList<String>): ArrayList<MedicamentoResponse> {
        var listaMedicamento = ArrayList<MedicamentoResponse>()
        lista.stream().forEach { medicamento ->
            val array = medicamento.split(";")
            listaMedicamento.add(
                MedicamentoResponse(
                    array[0].toInt(),
                    "",
                    array[2],
                    "",
                    0.00,
                    array[5].toDouble(),
                    array[6].toInt()
                )
            )
        }

        return listaMedicamento
    }

    private fun enviarDatosFragmentPedido(listaMedicamento: ArrayList<MedicamentoResponse>) {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_pedido) as NavHostFragment
        val fragment = navHostFragment.childFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_pedido) as PedidoFragment
        fragment.listaMedicamentosAgregados = listaMedicamento
        fragment.realizarDatosOperacionales()
        fragment.realizarAdapterPedido()
    }

    override fun onClickButtonRealizarPedido() {
        finish()
    }
}