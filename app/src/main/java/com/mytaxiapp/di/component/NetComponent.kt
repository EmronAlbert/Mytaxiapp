package com.mytaxiapp.di.component

import android.content.SharedPreferences

import com.mytaxiapp.di.module.AppModule
import com.mytaxiapp.di.module.NetModule

import javax.inject.Singleton

import dagger.Component
import retrofit.RestAdapter


/**
 * @author Tosin Onikute.
 */

@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface NetComponent {

    // downstream components need these exposed
    fun restAdapter(): RestAdapter

    fun sharedPreferences(): SharedPreferences

}
