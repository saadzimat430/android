package com.example.ecommerceproject.Sellers.Company;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ecommerceproject.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class UserLocation extends FragmentActivity{

    private static final int ERROR_DIALOG_REQUEST = 9001;
    GoogleMap mMap;
    private String userShippingAddress ="";
    Marker marker;
    Toolbar cToolbar;

    private static final double
            init_LAT = 0,
            init_LNG = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        if (servicesOK()) {
            setContentView(R.layout.activity_user_location);

            if (initMap()) {
                Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
                gotoLocation(init_LAT, init_LNG, 15);


            } else {
                Toast.makeText(this, "Ready to map!", Toast.LENGTH_SHORT).show();
            }

        } else {
            return;
        }


        cToolbar = findViewById(R.id.toolbarMap);
        cToolbar.setTitle("User Location");
//        setSupportActionBar(cToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        cToolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        cToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        userShippingAddress = bundle.get("location").toString();
    }

    public boolean servicesOK() {

        int isAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (GooglePlayServicesUtil.isUserRecoverableError(isAvailable)) {
            Dialog dialog =
                    GooglePlayServicesUtil.getErrorDialog(isAvailable, this, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to mapping service", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private boolean initMap() {
        if (mMap == null) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                        @Override
                                        public void onMapReady(GoogleMap googleMap) {
                                            mMap = googleMap;

                                            Geocoder gc = new Geocoder(getApplicationContext());
                                            List<Address> list = null;
                                            Intent intent;
                                            intent = getIntent();
                                            //String s = intent.getStringExtra("name");
                                            try {
                                                list = gc.getFromLocationName(userShippingAddress + ",Morocco", 1);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            if (list.size() > 0) {
                                                Address add = list.get(0);
                                                String locality = add.getLocality();
                                                double lat = add.getLatitude();
                                                double lng = add.getLongitude();
                                                gotoLocation(lat, lng, 15);

                                                if (marker != null) {
                                                    marker.remove();
                                                }

                                                addMarker(lat, lng);

                                            }
                                        }
                                    }


            );


        }
        return (mMap != null);
    }


    private void gotoLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);
    }

    private void hideSoftKeyboard(View v) {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    private void addMarker(double lat, double lng) {
        MarkerOptions options = new MarkerOptions()

                .position(new LatLng(lat, lng));

        marker = mMap.addMarker(options);

        marker = mMap.addMarker(options);
    }
}
