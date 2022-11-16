package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LibroFragment : Fragment(R.layout.fragment_libro) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_libro, container, false)

        return view
    }
}