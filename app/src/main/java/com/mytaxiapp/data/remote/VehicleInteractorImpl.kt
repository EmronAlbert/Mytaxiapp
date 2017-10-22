package com.mytaxiapp.data.remote

import android.app.Application

import com.mytaxiapp.data.model.Vehicles

import rx.Observable
import rx.functions.Func1

/**
 * @author Tosin Onikute.
 * *
 * * This is a Data Manager implementer class which contains methods, exposed for all the countries related data handling operations
 * * Main purpose of this is to decouple your class, thus making it cleaner and testable.
 * *
 * * fetchVehicles method is responsible for handling data retrieval for vehicles
 */

class VehicleInteractorImpl(private val application: Application) : VehicleInteractor {

    override fun fetchVehicles(vehicleInterface: VehicleInterface): Observable<Vehicles> {

        return vehicleInterface.vehicles
                .flatMap { vehicles -> Observable.just(vehicles) }
                .onErrorReturn { null }

    }

}
