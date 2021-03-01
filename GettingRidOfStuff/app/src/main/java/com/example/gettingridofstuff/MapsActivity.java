package com.example.gettingridofstuff;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LatLng selfGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        double lat = getIntent().getDoubleExtra("lat", 1);
        double longi = getIntent().getDoubleExtra("longi", 1);
        Toast toast = Toast.makeText(this, lat + ", " + longi, Toast.LENGTH_SHORT);
        toast.show();
        System.out.println(lat + ", " + longi);
        selfGPS = new LatLng(lat, longi);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Bham = new LatLng(48.751911, -122.478683);
        ArrayList<Charity> charList = MainActivity.db.selectAll();
        for (int i = 0; i < charList.size(); i++) {

            Charity curr = charList.get(i);
            LatLng gps = new LatLng(curr.getLatitude(), curr.getLongitude());
            mMap.addMarker(new MarkerOptions().position(gps).title(curr.getName()));
        }

        //mMap.addMarker(new MarkerOptions().position(Bham).title("Bham marker!!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bham, 13f));
    }
}