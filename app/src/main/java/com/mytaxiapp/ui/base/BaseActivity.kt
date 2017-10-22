package com.mytaxiapp.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.mytaxiapp.di.component.VehicleComponent


/**
 * @author Tosin Onikute.
 */

abstract class BaseActivity : AppCompatActivity() {

    private val component: VehicleComponent? = null

    protected abstract fun setupActivity(component: VehicleComponent?, savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity(component, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


}
