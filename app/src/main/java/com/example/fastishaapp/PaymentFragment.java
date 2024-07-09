package com.example.fastishaapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends Fragment {

    TextView itemType, txtDescription, txtFromLocation, txtToLocation, txtPrice;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance() {

        return new PaymentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemType = view.findViewById(R.id.txtItem);
        txtDescription = view.findViewById(R.id.txtDescription);
        txtFromLocation = view.findViewById(R.id.txtFromLocation);
        txtToLocation = view.findViewById(R.id.txtToLocation);

        Bundle bundle = getArguments();
        if(bundle !=null){
            String item = bundle.getString("itemType");
            String description = bundle.getString("description");
            String fromLocation = bundle.getString("fromLocation");
            String toLocation = bundle.getString("toLocation");

            itemType.setText(item);
            txtDescription.setText(description);
            txtFromLocation.setText(fromLocation);
            txtToLocation.setText(toLocation);

        }

    }

}