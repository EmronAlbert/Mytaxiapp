package com.mytaxiapp.data.remote


import com.mytaxiapp.data.model.Vehicles

import retrofit.Callback
import retrofit.http.GET
import rx.Observable

/**
 * @author Tosin Onikute.
 */

interface VehicleInterface {

    @GET("/vehicles.json")
    fun getVehicle2(response: Callback<Vehicles>)

    @get:GET("/vehicles.json")
    val vehicles: Observable<Vehicles>

}
