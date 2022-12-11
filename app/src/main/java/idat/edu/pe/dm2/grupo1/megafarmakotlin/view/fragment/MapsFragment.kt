package idat.edu.pe.dm2.grupo1.megafarmakotlin.view.fragment

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import idat.edu.pe.dm2.grupo1.megafarmakotlin.R

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        // Set the map coordinates to Kyoto Japan.LatLng kyoto = new LatLng(35.00116, 135.7681);
        val peru = LatLng(-12.0453, -77.0311)
        // Set the map type to Hybrid.
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        // Add a marker on the map coordinates.
        googleMap.addMarker(
            MarkerOptions()
                .position(peru)
                .title("peru")
        );
        // Move the camera to the map coordinates and zoom in closer.
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(peru))
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(15F))
        // Display traffic.
        googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID
        googleMap.isTrafficEnabled = true


        /*val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
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
}