package com.example.googlerecyclemap

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter
import com.google.android.gms.maps.model.Marker

class MainActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    private lateinit var PlasticFilterButton: ImageButton
    private lateinit var GlassFilterButton: ImageButton

    private lateinit var Cards: CardView

    val rostov = LatLng(47.233, 39.700)

    val second_marker = LatLng(47.234, 39.700)

    val TAG = "DEBUGVERSION"

    private var lastTouchedMarker: Marker? = null;

    internal inner class CustomInfoWindowAdapter: InfoWindowAdapter {

        private val window: View = layoutInflater.inflate(R.layout.custom_info_window, null)
        private val contents: View = layoutInflater.inflate(R.layout.custom_info_window, null)


        override fun getInfoContents(marker: Marker): View? {
            render(marker, window)
            return window
        }

        override fun getInfoWindow(marker: Marker): View? {
            render(marker, window)
            return window
        }

        private fun render(marker: Marker, view: View){
            var title = marker.title
            var tvTitle = view.findViewById<TextView>(R.id.titleText)

            if (!title.equals("")){
                tvTitle?.setText(title)
            }

            var snippet = marker.snippet
            var tvSnippet = view.findViewById<TextView>(R.id.snippet)

            if (!snippet.equals("")){
                tvSnippet?.setText(snippet)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Cards = findViewById(R.id.PlasticCard)

        Cards.setOnClickListener{
            Log.d(TAG, "Plastic card has been clicked.")
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setInfoWindowAdapter(CustomInfoWindowAdapter())

        mMap.addMarker(
            MarkerOptions()
                .position(second_marker)
                .title("Second Marker")
                .snippet("This is the second marker")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))

        mMap.addMarker(
            MarkerOptions()
                .position(rostov)
                .title("Rostov")
                .snippet("Hello World")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40)))

        mMap.setOnMarkerClickListener(this)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rostov, 16f))

        mMap.uiSettings.setMapToolbarEnabled(false)
    }

    /* Changes the color of selected marker */
    override fun onMarkerClick(marker: Marker): Boolean {
        if (lastTouchedMarker == null){
            lastTouchedMarker = marker
        }
        else{
            lastTouchedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin_40))
            lastTouchedMarker = marker
        }

        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.dark_green_pin_40))

        return false
    }

}