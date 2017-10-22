package com.mytaxiapp.di.module

import android.app.Application

import com.mytaxiapp.data.remote.VehicleInteractor
import com.mytaxiapp.data.remote.VehicleInteractorImpl
import com.mytaxiapp.ui.listvehicles.VehiclePresenter

import dagger.Module
import dagger.Provides

/**
 * @author Tosin Onikute.
 */

@Module
class VehicleModule(private val application: Application) {


    @Provides
    fun getVehiclePresenter(vehicleInteractor: VehicleInteractor): VehiclePresenter {
        return VehiclePresenter(application, vehicleInteractor)
    }


    @Provides
    internal fun provideVehicleFetcher(): VehicleInteractor {
        return VehicleInteractorImpl(application)
    }


}
