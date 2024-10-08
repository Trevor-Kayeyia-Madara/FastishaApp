package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<MainModel> itemList; // Change to MainModel

    public ItemAdapter(List<MainModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MainModel item = itemList.get(position);
        holder.orderNumber.setText(item.getOrderNumber());
        holder.description.setText(item.getDescription());
        holder.fromLocation.setText(item.getFromLocation());
        holder.toLocation.setText(item.getToLocation());
        // Set other fields as needed
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView orderNumber, description, fromLocation, toLocation;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            orderNumber = itemView.findViewById(R.id.order_number);
            description = itemView.findViewById(R.id.description); // Ensure this ID matches your item_layout
            fromLocation = itemView.findViewById(R.id.locationFom); // Ensure this ID matches your item_layout
            toLocation = itemView.findViewById(R.id.locationTo); // Ensure this ID matches your item_layout
        }
    }
}
