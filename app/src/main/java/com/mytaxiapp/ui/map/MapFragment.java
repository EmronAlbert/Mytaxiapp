package com.mytaxiapp.ui.map;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mytaxiapp.R;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    private ArrayList<LatLng> latlngs = new ArrayList<>();
    private ArrayList<String> address = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private int position = 0;

    private GoogleMap googleMap;

    private String mItem;
    private View rootView;
    private Context context;
    private String LATLNG = "latlng";
    private String ADDRESS = "address";
    private String NAMES = "names";
    private String POSITION = "position";

    public MapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(LATLNG)) {
            latlngs = getArguments().getParcelableArrayList(LATLNG);
        }

        if (getArguments().containsKey(ADDRESS)) {
            address = getArguments().getStringArrayList(ADDRESS);
        }

        if (getArguments().containsKey(NAMES)) {
            names = getArguments().getStringArrayList(NAMES);
        }

        if (getArguments().containsKey(POSITION)) {
            position = getArguments().getInt(POSITION);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_detail, container, false);
        context = this.getActivity();

        try{
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(MapFragment.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        if(googleMap != null) {
            setUpGoogleMap();
        }
    }

    private void setUpGoogleMap(){
        MarkerOptions marker = new MarkerOptions();
        if(latlngs.size() > 0 && address.size() > 0 && names.size() > 0) {
            for(int i=0; i<latlngs.size(); i++){
                marker.title(address.get(i).toString());
                marker.snippet(names.get(i).toString());
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi));
                marker.position(latlngs.get(i));
                googleMap.addMarker(marker);
            }

            googleMap.getUiSettings().setMapToolbarEnabled(false);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16));

            if(address.get(position) != null & names.get(position) != null) {
                updateMarker(latlngs.get(position), address.get(position), names.get(position));
            }
        }
    }

    public void updateMarker(LatLng latLng, String title, String snippet){

        // Updating marker options, so as to enable showInfoWindow() method
        // Which is only available as addMarker(marker).showInfoWindow()
        // for the purpose of highlighting the selected taxi
        MarkerOptions marker = new MarkerOptions();
        marker.title(title);
        marker.snippet(snippet);
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_taxi));
        marker.position(latLng);
        if(googleMap != null) {
            googleMap.addMarker(marker).showInfoWindow();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }

    public void onDestroyView() {
        try {
            Fragment fragment = (getChildFragmentManager().findFragmentById(R.id.map));
            if (fragment != null) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.remove(fragment);
                ft.commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onDestroyView();
    }

}
