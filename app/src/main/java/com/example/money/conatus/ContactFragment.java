package com.example.money.conatus;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ContactFragment extends Fragment {
    private SupportMapFragment mSupportMapFragment;
    private GoogleMap mGoogleMaps;

    public ContactFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mSupportMapFragment = new SupportMapFragment();
                mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        mGoogleMaps = googleMap;
                        mGoogleMaps.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        mGoogleMaps.getUiSettings().setZoomControlsEnabled(true);
                        LatLng akg = new LatLng(28.676655, 77.500823);
                        mGoogleMaps.addMarker(new MarkerOptions().position(akg).title("Conatus")
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_on_black_24dp))).showInfoWindow();

                        mGoogleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(akg, 15));

                    }
                });
                getChildFragmentManager().beginTransaction().add(R.id.mapView, mSupportMapFragment).commit();
            }
        },1000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        return view;
    }


}
