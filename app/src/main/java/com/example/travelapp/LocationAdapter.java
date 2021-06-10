package com.example.travelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private ArrayList<LocationModal> locationModalArrayList;
    private Context context;

    public LocationAdapter(ArrayList<LocationModal> locationModalArrayList, Context context) {
        this.locationModalArrayList = locationModalArrayList;
        this.context = context;
    }

    public void filterList(ArrayList<LocationModal> filterlist) {
        locationModalArrayList = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        LocationModal modal = locationModalArrayList.get(position);
        holder.locationNameTV.setText(modal.getLocationName());
        holder.locationDescTV.setText(modal.getLocationDescription());
    }

    @Override
    public int getItemCount() {
        return locationModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locationNameTV, locationDescTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            locationNameTV = itemView.findViewById(R.id.idTVLocationName);
            locationDescTV = itemView.findViewById(R.id.idTVLocationDescription);
        }
    }
}
