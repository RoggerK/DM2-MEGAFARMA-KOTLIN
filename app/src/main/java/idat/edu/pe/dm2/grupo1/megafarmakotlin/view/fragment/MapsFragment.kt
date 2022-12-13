package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R

class MapsFragment : Fragment(), GoogleMap.OnMarkerDragListener {

    private lateinit var mMap: GoogleMap
    private var coordenada = ""

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        mMap.setOnMarkerDragListener(this)
        var puntoInicio = LatLng(-12.01629, -76.88454)

        if (coordenada != "") {
            val array = coordenada.split(" ")
            puntoInicio = LatLng(array[0].toDouble(), array[1].toDouble())
        }

        mMap.addMarker(
            MarkerOptions()
                .position(puntoInicio)
                .title("Favor de indicar su coordenada")
                .draggable(true)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(puntoInicio, 16.0F))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener("llaveCliente", this,
            FragmentResultListener { requestKey, bundle ->
                coordenada = bundle.getString("coordenada").toString()
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        val bundle = Bundle()
        bundle.putString("coordenada", coordenada)
        parentFragmentManager.setFragmentResult("llaveMapa", bundle)
    }

    override fun onMarkerDragStart(p0: Marker) {
        p0.showInfoWindow()
    }

    override fun onMarkerDrag(p0: Marker) {
        var position = p0.position
        p0.snippet = position.latitude.toString() + " " + position.longitude.toString()
        p0.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0.position))

        coordenada = position.latitude.toString() + " " + position.longitude.toString()
    }

    override fun onMarkerDragEnd(p0: Marker) {
        p0.title = "Nueva ubicacion de referencia"
        p0.showInfoWindow()
        mMap.animateCamera(CameraUpdateFactory.newLatLng(p0.position))
    }

}