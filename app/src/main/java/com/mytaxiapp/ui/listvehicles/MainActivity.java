package com.mytaxiapp.ui.listvehicles;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mytaxiapp.BaseApplication;
import com.mytaxiapp.R;
import com.mytaxiapp.data.model.Placemark;
import com.mytaxiapp.data.remote.VehicleInterface;
import com.mytaxiapp.di.component.VehicleComponent;
import com.mytaxiapp.ui.base.BaseActivity;
import com.mytaxiapp.util.Logger;
import com.mytaxiapp.util.NetworkUtil;
import com.mytaxiapp.util.ui.MaterialProgressBar;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity implements VehicleView {

    @Inject
    VehiclePresenter presenter;

    @Inject
    VehicleInterface vehicleInterface;

    private Logger logger = Logger.getLogger(getClass());
    private CompositeSubscription mCompositeSubscription;
    private LinearLayout mainLayout;
    private VehicleListAdapter adapter;
    private RecyclerView recyclerView;
    private MaterialProgressBar progressBar;
    private LinearLayoutManager layoutManager;
    private Snackbar snackbarOffline;

    private ArrayList<Placemark> vehItemList;


    @Override
    protected void setupActivity(VehicleComponent component, Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ((BaseApplication) getApplication()).getVehicleComponent().inject(this);
        presenter.attachView(this);



        mCompositeSubscription = new CompositeSubscription();
        init();
        loadView();

    }

    // Initialize the view
    public void init() {

        mainLayout = (LinearLayout) findViewById(R.id.main_layout);
        progressBar = (MaterialProgressBar) findViewById(R.id.material_progress_bar);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.vehicles_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void loadView(){
        if(NetworkUtil.isConnected(getApplicationContext())) {
            vehicleList();
            hideOfflineSnackBar();
        } else {
            displayOfflineSnackbar();
        }
    }


    public void vehicleList(){
        presenter.getVehicleList(vehicleInterface, mCompositeSubscription);
    }

    public void setAdapter(ArrayList<Placemark> vehicleItemList){
        if(vehicleItemList.size() > 0) {
            vehItemList = vehicleItemList;
            adapter = new VehicleListAdapter(getApplicationContext(), vehicleItemList);
            recyclerView.setAdapter(adapter); // set adapter on recyclerview
        }
    }


    public void displayOfflineSnackbar() {
        snackbarOffline = Snackbar.make(mainLayout, R.string.no_connection_snackbar, Snackbar.LENGTH_INDEFINITE);
        TextView snackbarText = (TextView) snackbarOffline.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setTextColor(getApplicationContext().getResources().getColor(android.R.color.white));
        snackbarOffline.setAction(R.string.snackbar_action_retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadView();
            }
        });
        snackbarOffline.setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snackbarOffline.show();
    }


    public void hideOfflineSnackBar() {
        if (snackbarOffline != null && snackbarOffline.isShown()) {
            snackbarOffline.dismiss();
        }
    }

    @Override
    public void showLoading(){
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading(){
        progressBar.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        if (mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
        super.onDestroy();
    }

}
