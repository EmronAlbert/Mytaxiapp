package com.mytaxiapp.ui.listvehicles

import com.mytaxiapp.data.model.Placemark
import com.mytaxiapp.ui.base.MvpView

import java.util.ArrayList

/**
 * @author Tosin Onikute.
 */

interface VehicleView : MvpView {

    fun setAdapter(vehicleItemList: ArrayList<Placemark>)

    override fun showLoading()

    override fun hideLoading()
}
