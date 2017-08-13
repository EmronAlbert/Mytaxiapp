package com.mytaxiapp.data.remote;


import com.mytaxiapp.data.model.Vehicles;

import retrofit.Callback;
import retrofit.http.GET;
import rx.Observable;

/**
 * @author Tosin Onikute.
 */

public interface VehicleInterface {

    @GET("/vehicles.json")
    void getVehicle2(Callback<Vehicles> response);

    @GET("/vehicles.json")
    Observable<Vehicles> getVehicles();

}
