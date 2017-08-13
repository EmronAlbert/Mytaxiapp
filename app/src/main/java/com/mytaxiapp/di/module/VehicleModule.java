package com.mytaxiapp.di.module;

import android.app.Application;

import com.mytaxiapp.data.remote.VehicleInteractor;
import com.mytaxiapp.data.remote.VehicleInteractorImpl;
import com.mytaxiapp.ui.listvehicles.VehiclePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author Tosin Onikute.
 */

@Module
public class VehicleModule {

    private Application application;

    public VehicleModule(Application application){
        this.application = application;
    }


    @Provides
    public VehiclePresenter getVehiclePresenter(VehicleInteractor vehicleInteractor){
        return new VehiclePresenter(application, vehicleInteractor);
    }


    @Provides
    VehicleInteractor provideVehicleFetcher() {
        return new VehicleInteractorImpl( application );
    }



}
