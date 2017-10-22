package com.mytaxiapp.data.remote

import com.mytaxiapp.data.model.Vehicles

import rx.Observable

/**
 * @author Tosin Onikute.
 */

interface VehicleInteractor {

    fun fetchVehicles(vehicleInterface: VehicleInterface): Observable<Vehicles>

}
