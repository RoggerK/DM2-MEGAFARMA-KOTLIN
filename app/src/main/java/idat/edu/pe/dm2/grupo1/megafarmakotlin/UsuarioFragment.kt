package idat.edu.pe.dm2.grupo1.megafarmakotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import idat.edu.pe.dm2.grupo1.megafarmakotlin.interfaces.OnFramentUsuarioListerne


class UsuarioFragment : Fragment(R.layout.fragment_usuario), View.OnClickListener {

    private lateinit var listernerUsuario: OnFramentUsuarioListerne

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usuario, container, false)
        val buttonLibro = view.findViewById<Button>(R.id.btLibroReclamacion)
        val buttonAyuda = view.findViewById<Button>(R.id.btNecesitoAyuda)

        buttonLibro.setOnClickListener(this)
        buttonAyuda.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btLibroReclamacion -> listernerUsuario.onClickButtonUsuarioLibro()
            R.id.btNecesitoAyuda -> listernerUsuario.onClickButtonUsuarioAyuda()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listernerUsuario = context as OnFramentUsuarioListerne
        } catch (e: ClassCastException) {
            throw ClassCastException("$context debe implementar interfaz");
        }
    }
}