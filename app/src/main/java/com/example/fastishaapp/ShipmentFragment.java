package com.example.fastishaapp;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class ShipmentFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;
    private TextView fromLocation, myLocation, toLocation, txtSenderPhoneNo, txtReceiverPhoneNo;
    private ProgressBar progressBarOne, progressBarTwo;
    private EditText editTextToLocation, editDescription, edtItem, senderPhoneNo, receiverPhoneNo;

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
        fromLocation = view.findViewById(R.id.txtFromLocation);
        myLocation = view.findViewById(R.id.txtLocation);
        edtItem = view.findViewById(R.id.edtItem);
        editDescription = view.findViewById(R.id.edtDescription);
        editTextToLocation = view.findViewById(R.id.headTo);
        toLocation = view.findViewById(R.id.txtToLocation);
        senderPhoneNo = view.findViewById(R.id.userPhoneNO);
        receiverPhoneNo = view.findViewById(R.id.receiverPhoneNO);
        txtSenderPhoneNo = view.findViewById(R.id.txtSenderPhoneNo);
        txtReceiverPhoneNo = view.findViewById(R.id.txtReceiverPhoneNo);


        progressBarOne = view.findViewById(R.id.locationProgressBar);
        progressBarTwo = view.findViewById(R.id.locationTwoProgressBar);

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

                toLocation.setText(editTextToLocation.getText().toString());

                TextView txtItem = view.findViewById(R.id.txtItem);
                TextView txtDescription = view.findViewById(R.id.txtDescription);

                txtItem.setText(edtItem.getText().toString());
                txtDescription.setText(editDescription.getText().toString());

                txtSenderPhoneNo.setText(senderPhoneNo.getText().toString());
                txtReceiverPhoneNo.setText(receiverPhoneNo.getText().toString());
            }
        });

        Button shipment = view.findViewById(R.id.btnShipment);

        shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sPhoneNo =senderPhoneNo.getText().toString();
                String rPhoneNo =receiverPhoneNo.getText().toString();
                String itemType = edtItem.getText().toString();
                String description = editDescription.getText().toString();
                String fromLocationText = fromLocation.getText().toString();
                String toLocationText = editTextToLocation.getText().toString();

                if (itemType.isEmpty() || description.isEmpty() ||  toLocationText.isEmpty()) {
                    Toast.makeText(getContext(), "All details should be filled", Toast.LENGTH_SHORT).show();
                }else if(fromLocationText.isEmpty() ){
                    Toast.makeText(getContext(), "Wait until the App has detect your location", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), Process.class);
                    intent.putExtra("itemType", itemType);
                    intent.putExtra("description", description);
                    intent.putExtra("fromLocation", fromLocationText);
                    intent.putExtra("toLocation", toLocationText);
                    intent.putExtra("sPhoneNo", sPhoneNo);
                    intent.putExtra("rPhoneNo", rPhoneNo);

                    startActivity(intent);
                }
            }
        });
    }

    private void getLocation() {
        progressBarOne.setVisibility(View.VISIBLE);
        progressBarTwo.setVisibility(View.VISIBLE);
        try {
            locationManager = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);

            fromLocation.setText(address);
            myLocation.setText(address);
            progressBarOne.setVisibility(View.GONE);
            progressBarTwo.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        }
    }
}
