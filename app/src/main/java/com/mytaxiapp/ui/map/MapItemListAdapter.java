package com.mytaxiapp.ui.map;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.mytaxiapp.R;
import com.mytaxiapp.data.model.Placemark;

import java.util.ArrayList;

/**
 * @author Tosin Onikute.
 */

public class MapItemListAdapter
        extends RecyclerView.Adapter<MapItemListAdapter.ViewHolder> {

    private ArrayList<Placemark> placemarks;
    private Activity activityContext;

    public MapItemListAdapter(ArrayList<Placemark> placemarks, Activity activityContext) {
        this.placemarks = placemarks;
        this.activityContext = activityContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Placemark model = (Placemark) placemarks.get(position);

        holder.textName.setText(model.getName());

        final LatLng latlngs = new LatLng(model.getCoordinates().get(1), model.getCoordinates().get(0));
        final String title  = model.getAddress().toString();
        final String snippet  = model.getName().toString();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Activity is needed here for AppCompatActivity.getSupportFragmentManager
                MapFragment fragment = (MapFragment) ((AppCompatActivity) activityContext).getSupportFragmentManager().findFragmentById(R.id.item_detail_container);
                if(fragment != null) {
                    fragment.updateMarker(latlngs, title, snippet);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return placemarks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        //public final TextView textAddress;
        public final TextView textName;
        public final View lineBar;
        public final ImageView taxtiImage;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //textAddress = (TextView) view.findViewById(R.id.id);
            textName = (TextView) view.findViewById(R.id.content);
            lineBar = (View) view.findViewById(R.id.linebar);
            taxtiImage = (ImageView) view.findViewById(R.id.taxi_image);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + textName.getText() + "'";
        }
    }



}
