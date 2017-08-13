package com.mytaxiapp;

import android.app.Application;

import com.mytaxiapp.data.model.Placemark;
import com.mytaxiapp.data.model.Vehicles;
import com.mytaxiapp.data.remote.VehicleInteractor;
import com.mytaxiapp.data.remote.VehicleInterface;
import com.mytaxiapp.ui.listvehicles.VehiclePresenter;
import com.mytaxiapp.ui.listvehicles.VehicleView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/**
 * @author Tosin Onikute.
 */

public class VehicleInterfaceTest {

    @Mock
    VehicleInterface vehicleInterface;

    @Mock
    Application application;

    @Mock
    VehicleInteractor vehicleInteractor;

    @Mock
    VehicleView vehicleView;

    private VehiclePresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new VehiclePresenter(application, vehicleInteractor);
        presenter.attachView(vehicleView);
    }

    @Test
    public void testAPIResponse() throws Exception {
        // for this test, we use the first placemark
        String name = "HH-GO8522";
        String address = "Lesserstraße 170, 22049 Hamburg";
        String vin = "WME4513341K565439";

        // when
        when(vehicleInterface.getVehicles()).thenReturn(Observable.just(vehiclesList()));

        // given
        given(vehicleInterface.getVehicles()).willReturn(Observable.just(vehiclesList()));

        // When
        TestSubscriber<Vehicles> subscriber = new TestSubscriber<>();
        vehicleInterface.getVehicles().subscribe(subscriber);

        // Then
        subscriber.awaitTerminalEvent();
        subscriber.assertNoErrors();

        List<Vehicles> onNextEvents = subscriber.getOnNextEvents();
        List<Placemark> placemarkInfo = onNextEvents.get(0).getPlacemarks();

        // assert for name
        Assert.assertEquals(name, placemarkInfo.get(0).getName());

        // assert for address
        Assert.assertEquals(address, placemarkInfo.get(0).getAddress());

        // assert for vin
        Assert.assertEquals(vin, placemarkInfo.get(0).getVin());

    }

    private Vehicles vehiclesList() {

        // Setting up the values for test
        Vehicles vehicles = new Vehicles();

        List<Placemark> listPlacemarks = new ArrayList<Placemark>();

        List<Double> coordinates = new ArrayList<Double>();
        coordinates.add(10.07526);
        coordinates.add(53.59301);
        coordinates.add(0.0);

        Placemark placemark = new Placemark();
        placemark.setName("HH-GO8522");
        placemark.setAddress("Lesserstraße 170, 22049 Hamburg");
        placemark.setCoordinates(coordinates);
        placemark.setEngineType("CE");
        placemark.setExterior("UNACCEPTABLE");
        placemark.setFuel(42);
        placemark.setInterior("UNACCEPTABLE");
        placemark.setVin("WME4513341K565439");
        listPlacemarks.add(placemark);

        // add all the placemark data into Vehicle data structure
        vehicles.setPlacemarks(listPlacemarks);

        return vehicles;
    }

}
