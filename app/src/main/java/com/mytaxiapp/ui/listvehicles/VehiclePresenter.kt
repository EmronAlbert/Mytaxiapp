package com.mytaxiapp.ui.listvehicles

import android.app.Application

import com.mytaxiapp.data.model.Placemark
import com.mytaxiapp.data.model.Vehicles
import com.mytaxiapp.data.remote.VehicleInteractor
import com.mytaxiapp.data.remote.VehicleInterface
import com.mytaxiapp.ui.base.BasePresenter
import com.mytaxiapp.util.Logger

import java.util.ArrayList

import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * @author Tosin Onikute.
 */

class VehiclePresenter(private val application: Application, private val vehicleInteractor: VehicleInteractor) : BasePresenter<VehicleView>() {
    private val vehicleView: VehicleView? = null
    private val logger = Logger.getLogger(javaClass)

    override fun attachView(vehicleView: VehicleView) {
        super.attachView(vehicleView)
    }

    override fun detachView() {
        super.detachView()
    }


    fun getVehicleList(vehicleInterface: VehicleInterface, mCompositeSubscription: CompositeSubscription) {

        if(isViewAttached) {
            mvpView!!.showLoading()
        }

        mCompositeSubscription.add(vehicleInteractor.fetchVehicles(vehicleInterface)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ vehicles ->
                    if(isViewAttached) {
                        mvpView!!.hideLoading()
                        val vList = vehicles
                        val vehicleItemList = ArrayList(vList.placemarks!!)
                        mvpView!!.setAdapter(vehicleItemList)
                    }
                }) { throwable -> logger.debug(throwable.localizedMessage) })

    }

}
