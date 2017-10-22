package com.mytaxiapp.di.module

import android.app.Application

import com.mytaxiapp.data.remote.VehicleInteractor
import com.mytaxiapp.data.remote.VehicleInteractorImpl

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * @author Tosin Onikute.
 */

@Module
class AppModule(internal var mApplication: Application) {

    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return mApplication
    }

    @Provides
    internal fun provideDataManager(appDataManager: VehicleInteractorImpl): VehicleInteractor {
        return appDataManager
    }
}