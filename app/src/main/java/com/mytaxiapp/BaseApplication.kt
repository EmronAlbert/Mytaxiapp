package com.mytaxiapp

import android.app.Application

import com.mytaxiapp.di.component.DaggerNetComponent
import com.mytaxiapp.di.component.DaggerVehicleComponent
import com.mytaxiapp.di.component.NetComponent
import com.mytaxiapp.di.component.VehicleComponent
import com.mytaxiapp.di.module.AppModule
import com.mytaxiapp.di.module.NetModule
import com.mytaxiapp.di.module.RetrofitModule
import com.mytaxiapp.di.module.VehicleModule


/**
 * @author Tosin Onikute.
 */

class BaseApplication : Application() {

    companion object {
        @JvmStatic lateinit var netComponent: NetComponent
        @JvmStatic lateinit var vehicleComponent: VehicleComponent
    }

    override fun onCreate() {
        super.onCreate()

        netComponent = DaggerNetComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule())
                .build()

        vehicleComponent = DaggerVehicleComponent.builder()
                .netComponent(netComponent)
                .retrofitModule(RetrofitModule())
                .vehicleModule(VehicleModule(this))
                .build()

    }

}
