package com.mytaxiapp;

import android.app.Application;

import com.mytaxiapp.di.component.DaggerNetComponent;
import com.mytaxiapp.di.component.DaggerVehicleComponent;
import com.mytaxiapp.di.component.NetComponent;
import com.mytaxiapp.di.component.VehicleComponent;
import com.mytaxiapp.di.module.AppModule;
import com.mytaxiapp.di.module.NetModule;
import com.mytaxiapp.di.module.RetrofitModule;
import com.mytaxiapp.di.module.VehicleModule;


/**
 * @author Tosin Onikute.
 */

public class BaseApplication extends Application {

    private NetComponent mNetComponent;
    private VehicleComponent mVehicleComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule())
                .build();

        mVehicleComponent = DaggerVehicleComponent.builder()
                .netComponent(mNetComponent)
                .retrofitModule(new RetrofitModule())
                .vehicleModule(new VehicleModule(this))
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public VehicleComponent getVehicleComponent() {
        return mVehicleComponent;
    }

}
