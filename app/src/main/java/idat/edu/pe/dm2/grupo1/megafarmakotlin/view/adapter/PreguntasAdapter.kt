package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import idat.edu.pe.dm2.grupo1.megafarmakotlin.databinding.ItemPreguntasAyudaBinding
import idat.edu.pe.dm2.grupo1.megafarmakotlin.retrofit.response.PreguntaResponse

class PreguntasAdapter(private var listaPreguntas: List<PreguntaResponse>) :
    RecyclerView.Adapter<PreguntasAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemPreguntasAyudaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPreguntasAyudaBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listaPreguntas[position]) {
                binding.txvPregunta.text = pre
                binding.txvRespuestaPregunta.text = res
            }
        }
    }

    override fun getItemCount(): Int {
        return listaPreguntas.size
    }

}

