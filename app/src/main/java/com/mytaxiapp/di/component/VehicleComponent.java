package com.mytaxiapp.di.component;


import com.mytaxiapp.di.module.RetrofitModule;
import com.mytaxiapp.di.module.VehicleModule;
import com.mytaxiapp.di.scope.UserScope;
import com.mytaxiapp.ui.listvehicles.MainActivity;
import com.mytaxiapp.ui.map.MapActivity;

import dagger.Component;

/**
 * @author Tosin Onikute.
 */

@UserScope
@Component(dependencies = NetComponent.class, modules = {RetrofitModule.class, VehicleModule.class})
public interface VehicleComponent {



}
