package com.mytaxiapp.ui.listvehicles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mytaxiapp.R;
import com.mytaxiapp.data.model.Placemark;
import com.mytaxiapp.ui.map.MapActivity;
import com.mytaxiapp.util.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Tosin Onikute.
 */

public class VehicleListAdapter
        extends RecyclerView.Adapter<VehicleListAdapter.ViewHolder> {

    private final Logger logger = Logger.getLogger(getClass());
    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private ArrayList<Placemark> placemarks;
    private Context context;

    private String caddress;
    private String cname;
    private String cinterior;
    private String cfuel;

    private String INTERIOR = "INTERIOR";
    private String VIN = "VIN";
    private String FUEL = "FUEL";
    private String ENGINE = "ENGINE";

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView textAddress;
        public final TextView textName;
        public final TextView textInterior;
        public final TextView textFuel;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            textAddress = (TextView) view.findViewById(R.id.address);
            textName = (TextView) view.findViewById(R.id.name);
            textInterior = (TextView) view.findViewById(R.id.interior_vin);
            textFuel = (TextView) view.findViewById(R.id.fuel_engine);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public String getValueAt(int position) {
        return String.valueOf(placemarks.get(position).getName());
    }

    public VehicleListAdapter(Context context, ArrayList<Placemark> placemarks) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        this.context = context;
        this.placemarks = placemarks;
        mBackground = mTypedValue.resourceId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vehicle_list, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        /* Set your values */
        final Placemark model = (Placemark) placemarks.get(position);

        caddress = "";
        cname = "";
        cinterior = "";
        cfuel = "";

        if (model.getAddress() != null) {
            caddress = model.getAddress();
        }
        if (model.getName() != null) {
            cname = model.getName();
        }
        if (model.getInterior() != null && model.getVin() != null) {
            cinterior = INTERIOR + " : " + model.getInterior() + " | " + VIN + " : " + model.getVin();
        }
        if (model.getFuel() != null && model.getEngineType() != null) {
            cfuel = FUEL + " : " + model.getFuel() + "  |  " + ENGINE + " : " + model.getEngineType();
        }

        holder.textAddress.setText(caddress);
        holder.textName.setText(cname);
        holder.textInterior.setText(cinterior);
        holder.textFuel.setText(cfuel);

        // launch the detail activity to show vehicle information
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("position", holder.getAdapterPosition());
                Activity activity = (Activity) v.getContext();
                activity.startActivityForResult(intent, 500);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != placemarks ? placemarks.size() : 0);
    }

    public void addAll(List<Placemark> data){
        notifyDataSetChanged();
    }

    public void add(Placemark data){
        notifyDataSetChanged();
        placemarks.add(data);
    }


    public Placemark getItemPos(int pos){
        return placemarks.get(pos);
    }

    public void clear(){
        placemarks.clear();
    }

}

