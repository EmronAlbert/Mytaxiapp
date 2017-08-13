package com.mytaxiapp.data.remote;

import android.app.Application;

import com.mytaxiapp.data.model.Vehicles;

import rx.Observable;
import rx.functions.Func1;

/**
 * @author Tosin Onikute.
 *
 * This is a Data Manager implementer class which contains methods, exposed for all the countries related data handling operations
 * Main purpose of this is to decouple your class, thus making it cleaner and testable.
 *
 * fetchVehicles method is responsible for handling data retrieval for vehicles
 *
 */

public class VehicleInteractorImpl implements VehicleInteractor {

    private final Application application;

    public VehicleInteractorImpl(Application application) {
        this.application = application;
    }

    public Observable<Vehicles> fetchVehicles(VehicleInterface vehicleInterface){

        return vehicleInterface.getVehicles()
                .flatMap(new Func1<Vehicles, Observable<Vehicles>>() {
                    @Override
                    public Observable<Vehicles> call(Vehicles vehicles) {
                        return Observable.just(vehicles);
                    }
                })
                .onErrorReturn(new Func1<Throwable, Vehicles>() {
                    @Override
                    public Vehicles call(Throwable thr) {
                        return null;
                    }
                });

    }

}
