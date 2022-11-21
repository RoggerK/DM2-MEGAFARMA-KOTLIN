package idat.edu.pe.dm2.grupo1.megafarmakotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ActivityAyudaBinding

class AyudaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAyudaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAyudaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}