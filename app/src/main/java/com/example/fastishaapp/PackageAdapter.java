package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PackageAdapter extends RecyclerView.Adapter<PackageAdapter.PackageViewHolder> {

    private final List<PackageData> packageList;

    public PackageAdapter(List<PackageData> packageList) {
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item_layout, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        PackageData packageData = packageList.get(position);
        holder.itemTypeTextView.setText(packageData.getItem());
        holder.descriptionTextView.setText(packageData.getDescription());
        holder.fromLocationTextView.setText(packageData.getFromLocation());
        holder.toLocationTextView.setText(packageData.getToLocation());
        // Set other fields...
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {

        TextView itemTypeTextView, descriptionTextView, fromLocationTextView, toLocationTextView;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTypeTextView = itemView.findViewById(R.id.itemTypeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            //fromLocationTextView = itemView.findViewById(R.id.fromLocationTextView);
            //toLocationTextView = itemView.findViewById(R.id.toLocationTextView);
        }
    }
}
