package com.mytaxiapp.di.module

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.google.gson.GsonBuilder
import com.mytaxiapp.Api

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit.RestAdapter
import retrofit.converter.GsonConverter

/**
 * @author Tosin Onikute.
 */

@Module
class NetModule {

    @Provides
    @Singleton
    internal fun providesSharedPreferences(application: Application): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(application)
    }

    @Provides
    @Singleton
    internal fun provideRestAdapter(): RestAdapter {
        val restAdapter = RestAdapter.Builder()
                .setEndpoint(Api.BASE_URL)
                .setConverter(GsonConverter(GsonBuilder().create()))
                //.setLogLevel(RestAdapter.LogLevel.FULL)
                .build()

        return restAdapter
    }


}
