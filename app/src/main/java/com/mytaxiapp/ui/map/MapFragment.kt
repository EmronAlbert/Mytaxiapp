package com.mytaxiapp.ui.map

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mytaxiapp.R

import java.util.ArrayList


class MapFragment : Fragment(), OnMapReadyCallback {

    private var latlngs = ArrayList<LatLng>()
    private var address = ArrayList<String>()
    private var names = ArrayList<String>()
    private var position = 0

    private var googleMap: GoogleMap? = null

    private val mItem: String? = null
    private var rootView: View? = null
    private var mContext: Context? = null
    private val LATLNG = "latlng"
    private val ADDRESS = "address"
    private val NAMES = "names"
    private val POSITION = "position"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments.containsKey(LATLNG)) {
            latlngs = arguments.getParcelableArrayList<LatLng>(LATLNG)
        }

        if (arguments.containsKey(ADDRESS)) {
            address = arguments.getStringArrayList(ADDRESS)
        }

        if (arguments.containsKey(NAMES)) {
            names = arguments.getStringArrayList(NAMES)
        }

        if (arguments.containsKey(POSITION)) {
            position = arguments.getInt(POSITION)
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater!!.inflate(R.layout.item_detail, container, false)
        mContext = this.activity

        try {
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this@MapFragment)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return rootView
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        if (googleMap != null) {
            setUpGoogleMap()
        }
    }

    private fun setUpGoogleMap() {
        val marker = MarkerOptions()
        if (latlngs.size > 0 && address.size > 0 && names.size > 0) {
            for (i in latlngs.indices) {
                marker.title(address[i].toString())
                marker.snippet(names[i].toString())
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi))
                marker.position(latlngs[i])
                googleMap!!.addMarker(marker)
            }

            googleMap!!.uiSettings.isMapToolbarEnabled = false
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 16f))

            if ((address[position] != null) and (names[position] != null)) {
                updateMarker(latlngs[position], address[position], names[position])
            }
        }
    }

    fun updateMarker(latLng: LatLng, title: String, snippet: String) {

        // Updating marker options, so as to enable showInfoWindow() method
        // Which is only available as addMarker(marker).showInfoWindow()
        // for the purpose of highlighting the selected taxi
        val marker = MarkerOptions()
        marker.title(title)
        marker.snippet(snippet)
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi))
        marker.position(latLng)
        if (googleMap != null) {
            googleMap!!.addMarker(marker).showInfoWindow()
            googleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))
        }
    }

    override fun onDestroyView() {
        try {
            val fragment = childFragmentManager.findFragmentById(R.id.map)
            if (fragment != null) {
                val ft = activity.supportFragmentManager.beginTransaction()
                ft.remove(fragment)
                ft.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        super.onDestroyView()
    }

}
