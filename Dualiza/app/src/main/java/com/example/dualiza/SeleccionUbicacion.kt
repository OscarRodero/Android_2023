package com.example.dualiza

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dualiza.databinding.ActivitySeleccionUbicacionBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.PointOfInterest
import android.Manifest
import android.app.Activity
import android.content.Intent
import com.google.android.gms.maps.model.MarkerOptions


class SeleccionUbicacion : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnPoiClickListener, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {
    lateinit var binding: ActivitySeleccionUbicacionBinding
    private val LOCATION_REQUEST_CODE: Int = 0

    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap

    var alMarcadores = ArrayList<Marker>()

    companion object {
        const val REQUEST_CODE_LOCATION = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeleccionUbicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mapView = binding.mapa
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        binding.btnSeleccionarPosicion.setOnClickListener(){
            val resultadoIntent = Intent()
            resultadoIntent.putExtra("Latitud", binding.etxtLatitud.text)
            resultadoIntent.putExtra("Longitud", binding.etxtLongitud.text)
            setResult(Activity.RESULT_OK, resultadoIntent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.setOnMapLongClickListener(this)
        map.setOnMarkerClickListener(this)
        enableMyLocation()
    }
    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if(!::map.isInitialized) return
        if(isPermissionsGranted()){
            map.isMyLocationEnabled=true
        }else{
            requestLocationPermission()
        }
    }

    fun isPermissionsGranted() = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Permisos insuficientes, modifícalos desde Ajustes", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
        }
    }
    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onPoiClick(p0: PointOfInterest) {
        TODO("Not yet implemented")
    }

    override fun onMapLongClick(p0: LatLng) {
        var marcador = map.addMarker(MarkerOptions().position(p0!!).title("Ubicación"))
        if(alMarcadores.isEmpty()){
            alMarcadores.add(marcador!!)
        }else{
            alMarcadores.removeFirst()
            alMarcadores.add(marcador!!)
        }
        binding.etxtLatitud.setText(p0!!.latitude.toString())
        binding.etxtLongitud.setText(p0!!.longitude.toString())
    }

    override fun onMarkerClick(p0: Marker): Boolean {
        TODO("Not yet implemented")
    }
}