package com.mytaxiapp.di.module;

import android.app.Application;

import com.mytaxiapp.data.remote.VehicleInteractor;
import com.mytaxiapp.data.remote.VehicleInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Tosin Onikute.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    VehicleInteractor provideDataManager(VehicleInteractorImpl appDataManager) {
        return appDataManager;
    }
}