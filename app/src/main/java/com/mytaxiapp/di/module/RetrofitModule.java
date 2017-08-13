package com.mytaxiapp.di.module;


import com.mytaxiapp.data.remote.VehicleInterface;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;


/**
 * @author Tosin Onikute.
 */

@Module
public class RetrofitModule {

    @Provides
    public VehicleInterface providesVehicleInterface(RestAdapter restAdapter) {
        return restAdapter.create(VehicleInterface.class);
    }
}