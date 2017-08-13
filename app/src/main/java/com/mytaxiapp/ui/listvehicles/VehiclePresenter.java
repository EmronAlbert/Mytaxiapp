package com.mytaxiapp.ui.listvehicles;

import android.app.Application;

import com.mytaxiapp.data.model.Placemark;
import com.mytaxiapp.data.model.Vehicles;
import com.mytaxiapp.data.remote.VehicleInteractor;
import com.mytaxiapp.data.remote.VehicleInterface;
import com.mytaxiapp.ui.base.BasePresenter;
import com.mytaxiapp.util.Logger;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Tosin Onikute.
 */

public class VehiclePresenter extends BasePresenter<VehicleView> {

    private final Application application;
    private VehicleInteractor vehicleInteractor;
    private VehicleView vehicleView;
    private Logger logger = Logger.getLogger(getClass());


    public VehiclePresenter(Application application, VehicleInteractor vehicleInteractor) {
        this.application = application;
        this.vehicleInteractor = vehicleInteractor;
    }

    @Override
    public void attachView(VehicleView vehicleView){
        super.attachView(vehicleView);
    }

    @Override
    public void detachView(){
        super.detachView();
    }


    public void getVehicleList(VehicleInterface vehicleInterface, CompositeSubscription mCompositeSubscription){

        getMvpView().showLoading();

        mCompositeSubscription.add(vehicleInteractor.fetchVehicles(vehicleInterface)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Vehicles>() {
                    @Override
                    public void call(Vehicles vehicles) {

                        getMvpView().hideLoading();
                        Vehicles vList = vehicles;

                        ArrayList<Placemark> vehicleItemList = new ArrayList<Placemark>(vList.getPlacemarks());
                        getMvpView().setAdapter(vehicleItemList);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        logger.debug(throwable.getLocalizedMessage());
                    }
                }));

    }

}
