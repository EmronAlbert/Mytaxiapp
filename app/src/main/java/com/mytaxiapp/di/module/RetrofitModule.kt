package com.mytaxiapp.di.module


import com.mytaxiapp.data.remote.VehicleInterface

import dagger.Module
import dagger.Provides
import retrofit.RestAdapter


/**
 * @author Tosin Onikute.
 */

@Module
class RetrofitModule {

    @Provides
    fun providesVehicleInterface(restAdapter: RestAdapter): VehicleInterface {
        return restAdapter.create(VehicleInterface::class.java)
    }
}