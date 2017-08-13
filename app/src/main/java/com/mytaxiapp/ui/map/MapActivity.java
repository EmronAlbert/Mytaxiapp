package com.mytaxiapp.ui.map;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mytaxiapp.BaseApplication;
import com.mytaxiapp.R;
import com.mytaxiapp.data.model.Placemark;
import com.mytaxiapp.data.remote.VehicleInterface;
import com.mytaxiapp.di.component.VehicleComponent;
import com.mytaxiapp.ui.base.BaseActivity;
import com.mytaxiapp.ui.listvehicles.VehiclePresenter;
import com.mytaxiapp.ui.listvehicles.VehicleView;
import com.mytaxiapp.util.Logger;
import com.mytaxiapp.util.NetworkUtil;
import com.mytaxiapp.util.ui.MaterialProgressBar;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

public class MapActivity extends BaseActivity implements VehicleView {

    @Inject
    VehiclePresenter presenter;

    @Inject
    VehicleInterface vehicleInterface;

    private Logger logger = Logger.getLogger(getClass());
    private CompositeSubscription mCompositeSubscription;
    private MaterialProgressBar progressBar;
    private Snackbar snackbarOffline;
    private LinearLayout mainLayout;
    private RecyclerView recyclerView;

    private String LATLNG = "latlng";
    private String ADDRESS = "address";
    private String NAMES = "names";
    private String POSITION = "position";
    private int adapterPosition = 0;

    @Override
    protected void setupActivity(VehicleComponent component, Bundle savedInstanceState) {
        setContentView(R.layout.activity_map);
        ((BaseApplication) getApplication()).getVehicleComponent().inject(this);
        presenter.attachView(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCompositeSubscription = new CompositeSubscription();
        init();
        loadView();
        setAdapterPosition();


    }

    // Initialize the view
    public void init() {
        mainLayout = (LinearLayout) findViewById(R.id.map_layout);
        progressBar = (MaterialProgressBar) findViewById(R.id.material_progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
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

    private void setAdapterPosition(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            adapterPosition = extras.getInt("position");
        }
    }

    public void vehicleList(){
        presenter.getVehicleList(vehicleInterface, mCompositeSubscription);
    }

    public void setAdapter(ArrayList<Placemark> vehicleItemList){
        if(vehicleItemList.size() > 0) {
            recyclerView.setAdapter(new MapItemListAdapter(vehicleItemList, MapActivity.this));
            recyclerView.getLayoutManager().scrollToPosition(adapterPosition);


            // fragment setup
            ArrayList<LatLng> latlngs = new ArrayList<>();
            ArrayList<String> address = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();

            for(Placemark placemark: vehicleItemList){
                latlngs.add(new LatLng(placemark.getCoordinates().get(1), placemark.getCoordinates().get(0)));
                address.add(placemark.getAddress().toString());
                names.add(placemark.getName().toString());
            }

            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList(LATLNG, latlngs);
            arguments.putStringArrayList(ADDRESS, address);
            arguments.putStringArrayList(NAMES, names);
            arguments.putInt(POSITION, adapterPosition);
            MapFragment fragment = new MapFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
