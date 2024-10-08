package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<MainModel> itemList;

    public ItemAdapter(List<MainModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MainModel item = itemList.get(position);
        holder.itemTextView.setText(item.getItem());
        holder.descriptionTextView.setText(item.getDescription());
        holder.transactionCodeTextView.setText(item.getTransactionCode());
        holder.dateTextView.setText(item.getDate());
        holder.timeTextView.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView, descriptionTextView, transactionCodeTextView, dateTextView, timeTextView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTextView = itemView.findViewById(R.id.itemTypeTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            //transactionCodeTextView = itemView.findViewById(R.id.transactionCodeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
        }
    }
}
