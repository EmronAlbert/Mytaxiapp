package com.mytaxiapp.ui.listvehicles;

import com.mytaxiapp.data.model.Placemark;
import com.mytaxiapp.ui.base.MvpView;

import java.util.ArrayList;

/**
 * @author Tosin Onikute.
 */

public interface VehicleView extends MvpView {

    void setAdapter(ArrayList<Placemark> vehicleItemList);

    void showLoading();

    void hideLoading();
}
