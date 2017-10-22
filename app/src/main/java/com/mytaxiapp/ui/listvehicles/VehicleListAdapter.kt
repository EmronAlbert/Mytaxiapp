package com.mytaxiapp.ui.listvehicles

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mytaxiapp.R
import com.mytaxiapp.data.model.Placemark
import com.mytaxiapp.ui.map.MapActivity
import com.mytaxiapp.util.Logger

import java.util.ArrayList


/**
 * @author Tosin Onikute.
 */

class VehicleListAdapter(private val context: Context, private val placemarks: ArrayList<Placemark>?) : RecyclerView.Adapter<VehicleListAdapter.ViewHolder>() {

    private val logger = Logger.getLogger(javaClass)
    private val mTypedValue = TypedValue()
    private val mBackground: Int

    private var caddress: String? = null
    private var cname: String? = null
    private var cinterior: String? = null
    private var cfuel: String? = null

    private val INTERIOR = "INTERIOR"
    private val VIN = "VIN"
    private val FUEL = "FUEL"
    private val ENGINE = "ENGINE"

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textAddress: TextView
        val textName: TextView
        val textInterior: TextView
        val textFuel: TextView

        init {

            textAddress = mView.findViewById(R.id.address) as TextView
            textName = mView.findViewById(R.id.name) as TextView
            textInterior = mView.findViewById(R.id.interior_vin) as TextView
            textFuel = mView.findViewById(R.id.fuel_engine) as TextView
        }

        override fun toString(): String {
            return super.toString()
        }
    }

    fun getValueAt(position: Int): String {
        return placemarks!![position].name.toString()
    }

    init {
        context.theme.resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true)
        mBackground = mTypedValue.resourceId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.vehicle_list, parent, false)
        view.setBackgroundResource(mBackground)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /* Set your values */
        val model = placemarks!![position]

        caddress = ""
        cname = ""
        cinterior = ""
        cfuel = ""

        if (model.address != null) {
            caddress = model.address
        }
        if (model.name != null) {
            cname = model.name
        }
        if (model.interior != null && model.vin != null) {
            cinterior = INTERIOR + " : " + model.interior + " | " + VIN + " : " + model.vin
        }
        if (model.fuel != null && model.engineType != null) {
            cfuel = FUEL + " : " + model.fuel + "  |  " + ENGINE + " : " + model.engineType
        }

        holder.textAddress.text = caddress
        holder.textName.text = cname
        holder.textInterior.text = cinterior
        holder.textFuel.text = cfuel

        // launch the detail activity to show vehicle information
        holder.mView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("position", holder.adapterPosition)
            val activity = v.context as Activity
            activity.startActivityForResult(intent, 500)
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

    }

    override fun getItemCount(): Int {
        return placemarks?.size ?: 0
    }

    fun addAll(data: List<Placemark>) {
        notifyDataSetChanged()
    }

    fun add(data: Placemark) {
        notifyDataSetChanged()
        placemarks!!.add(data)
    }


    fun getItemPos(pos: Int): Placemark {
        return placemarks!![pos]
    }

    fun clear() {
        placemarks!!.clear()
    }

}

