package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.date.setText(model.getDate());
        holder.description.setText(model.getDescription());
        holder.fromLocation.setText(model.getFromLocation());
        holder.item.setText(model.getItem());
        holder.receiverPhoneNo.setText(model.getReceiverPhoneNo());
        holder.senderPhoneNo.setText(model.getSenderPhoneNo());
        holder.time.setText(model.getTime());
        holder.transactionCode.setText(model.getTransactionCode());
        holder.toLocation.setText(model.getToLocation());
        holder.transactionCode.setText(model.getTransactionCode());


    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView date, description, fromLocation, item, receiverPhoneNo, senderPhoneNo, time, toLocation, transactionCode, email;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
            fromLocation = itemView.findViewById(R.id.locationFrom);
            item = itemView.findViewById(R.id.item);
            receiverPhoneNo = itemView.findViewById(R.id.receiverPhoneNo);
            senderPhoneNo = itemView.findViewById(R.id.senderPhoneNo);
            time = itemView.findViewById(R.id.time);
            transactionCode = itemView.findViewById(R.id.order_number);
        }
    }
}
