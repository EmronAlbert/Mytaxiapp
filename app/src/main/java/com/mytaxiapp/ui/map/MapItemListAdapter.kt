package com.mytaxiapp.ui.map

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.google.android.gms.maps.model.LatLng
import com.mytaxiapp.R
import com.mytaxiapp.data.model.Placemark

import java.util.ArrayList

/**
 * @author Tosin Onikute.
 */

class MapItemListAdapter(private val placemarks: ArrayList<Placemark>, private val activityContext: Activity) : RecyclerView.Adapter<MapItemListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = placemarks[position]

        holder.textName.text = model.name

        val latlngs = LatLng(model.coordinates!![1], model.coordinates!![0])
        val title = model.address!!.toString()
        val snippet = model.name!!.toString()

        holder.mView.setOnClickListener {
            // Activity is needed here for AppCompatActivity.getSupportFragmentManager
            val fragment = (activityContext as AppCompatActivity).supportFragmentManager.findFragmentById(R.id.item_detail_container) as MapFragment
            fragment?.updateMarker(latlngs, title, snippet)
        }
    }

    override fun getItemCount(): Int {
        return placemarks.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //public final TextView textAddress;
        val textName: TextView
        val lineBar: View
        val taxtiImage: ImageView

        init {
            //textAddress = (TextView) view.findViewById(R.id.id);
            textName = mView.findViewById(R.id.content) as TextView
            lineBar = mView.findViewById(R.id.linebar) as View
            taxtiImage = mView.findViewById(R.id.taxi_image) as ImageView
        }

        override fun toString(): String {
            return super.toString() + " '" + textName.text + "'"
        }
    }


}
