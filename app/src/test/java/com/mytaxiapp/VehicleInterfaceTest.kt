package com.mytaxiapp

import android.app.Application

import com.mytaxiapp.data.model.Placemark
import com.mytaxiapp.data.model.Vehicles
import com.mytaxiapp.data.remote.VehicleInteractor
import com.mytaxiapp.data.remote.VehicleInterface
import com.mytaxiapp.ui.listvehicles.VehiclePresenter
import com.mytaxiapp.ui.listvehicles.VehicleView

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import java.util.ArrayList

import rx.Observable
import rx.observers.TestSubscriber

import org.mockito.BDDMockito.given
import org.mockito.Mockito.`when`

/**
 * @author Tosin Onikute.
 */

class VehicleInterfaceTest {

    @Mock
    internal var vehicleInterface: VehicleInterface? = null

    @Mock
    internal var application: Application? = null

    @Mock
    internal var vehicleInteractor: VehicleInteractor? = null

    @Mock
    internal var vehicleView: VehicleView? = null

    private var presenter: VehiclePresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = VehiclePresenter(application!!, vehicleInteractor!!)
        presenter!!.attachView(vehicleView!!)
    }

    @Test
    @Throws(Exception::class)
    fun testAPIResponse() {
        // for this test, we use the first placemark
        val name = "HH-GO8522"
        val address = "Lesserstraße 170, 22049 Hamburg"
        val vin = "WME4513341K565439"

        // when
        `when`(vehicleInterface!!.vehicles).thenReturn(Observable.just(vehiclesList()))

        // given
        given(vehicleInterface!!.vehicles).willReturn(Observable.just(vehiclesList()))

        // When
        val subscriber = TestSubscriber<Vehicles>()
        vehicleInterface!!.vehicles.subscribe(subscriber)

        // Then
        subscriber.awaitTerminalEvent()
        subscriber.assertNoErrors()

        val onNextEvents = subscriber.onNextEvents
        val placemarkInfo = onNextEvents[0].placemarks

        // assert for name
        Assert.assertEquals(name, placemarkInfo!![0].name)

        // assert for address
        Assert.assertEquals(address, placemarkInfo[0].address)

        // assert for vin
        Assert.assertEquals(vin, placemarkInfo[0].vin)

    }

    private fun vehiclesList(): Vehicles {

        // Setting up the values for test
        val vehicles = Vehicles()

        val listPlacemarks = ArrayList<Placemark>()

        val coordinates = ArrayList<Double>()
        coordinates.add(10.07526)
        coordinates.add(53.59301)
        coordinates.add(0.0)

        val placemark = Placemark()
        placemark.name = "HH-GO8522"
        placemark.address = "Lesserstraße 170, 22049 Hamburg"
        placemark.coordinates = coordinates
        placemark.engineType = "CE"
        placemark.exterior = "UNACCEPTABLE"
        placemark.fuel = 42
        placemark.interior = "UNACCEPTABLE"
        placemark.vin = "WME4513341K565439"
        listPlacemarks.add(placemark)

        // add all the placemark data into Vehicle data structure
        vehicles.placemarks = listPlacemarks

        return vehicles
    }

}
