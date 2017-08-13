package com.mytaxiapp.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mytaxiapp.di.component.VehicleComponent;


/**
 * @author Tosin Onikute.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private VehicleComponent component;

    protected abstract void setupActivity(VehicleComponent component, Bundle savedInstanceState);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivity(component, savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



}
