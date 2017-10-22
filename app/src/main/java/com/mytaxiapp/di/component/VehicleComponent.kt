package com.mytaxiapp.di.component


import com.mytaxiapp.di.module.RetrofitModule
import com.mytaxiapp.di.module.VehicleModule
import com.mytaxiapp.di.scope.UserScope
import com.mytaxiapp.ui.listvehicles.MainActivity
import com.mytaxiapp.ui.map.MapActivity

import dagger.Component

/**
 * @author Tosin Onikute.
 */

@UserScope
@Component(dependencies = arrayOf(NetComponent::class), modules = arrayOf(RetrofitModule::class, VehicleModule::class))
interface VehicleComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mapActivity: MapActivity)

}
