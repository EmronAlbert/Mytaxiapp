package com.mytaxiapp.data.remote;

import com.mytaxiapp.data.model.Vehicles;

import rx.Observable;

/**
 * @author Tosin Onikute.
 */

public interface VehicleInteractor {

    Observable<Vehicles> fetchVehicles(VehicleInterface vehicleInterface);

}
