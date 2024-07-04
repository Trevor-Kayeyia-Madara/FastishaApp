package com.example.fastishaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.firstNameTextView.setText(user.getFirstName());
        holder.lastNameTextView.setText(user.getLastName());
        holder.emailTextView.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView firstNameTextView;
        public TextView lastNameTextView;
        public TextView emailTextView;


        public UserViewHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.customer_first_name);
            lastNameTextView = itemView.findViewById(R.id.customer_last_name);
            emailTextView = itemView.findViewById(R.id.customer_email);
            // ... find other views
        }
    }
}
