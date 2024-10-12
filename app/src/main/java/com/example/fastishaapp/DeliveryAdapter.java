package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private List<Delivery> deliveryList;

    public DeliveryAdapter(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delivery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery delivery = deliveryList.get(position);
        holder.productNameTextView.setText("Product: " + delivery.getProductName());
        holder.destinationTextView.setText("Destination: " + delivery.getDestination());
        holder.weightTextView.setText("Weight: " + delivery.getWeight() + " kg");
        holder.priceTextView.setText("Total Price: Sh. " + delivery.getTotalPrice());
        holder.dateTextView.setText("Date: " + delivery.getDate());
        holder.locationTextView.setText("Location: " + delivery.getLocation());
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView, destinationTextView, weightTextView, priceTextView, dateTextView, locationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productName);
            destinationTextView = itemView.findViewById(R.id.destination);
            weightTextView = itemView.findViewById(R.id.weight);
            priceTextView = itemView.findViewById(R.id.price);
            dateTextView = itemView.findViewById(R.id.date);
            locationTextView = itemView.findViewById(R.id.location);
        }
    }
}
