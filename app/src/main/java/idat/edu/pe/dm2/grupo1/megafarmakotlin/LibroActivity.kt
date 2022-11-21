package idat.edu.pe.dm2.grupo1.megafarmakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityLibroBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.pojo.LoginResponse

class LibroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLibroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = intent
            .getSerializableExtra("token") as String

        println("token: $token")
    }
}