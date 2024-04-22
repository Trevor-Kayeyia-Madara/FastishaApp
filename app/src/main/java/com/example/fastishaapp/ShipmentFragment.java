package com.example.fastishaapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ShipmentFragment extends Fragment {

    public ShipmentFragment() {
        // Required empty public constructor
    }

    public static ShipmentFragment newInstance() {
        return new ShipmentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button confirm = view.findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtItem = view.findViewById(R.id.edtItem);
                TextView txtItem = view.findViewById(R.id.txtItem);
                String itemText = edtItem.getText().toString();
                txtItem.setText(itemText);
            }
        });
    }

    // Removed unused method

}
