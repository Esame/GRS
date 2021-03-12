package com.example.gettingridofstuff;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    LatLng selfGPS;
    private static final int REQUEST_LOCATION = 1;
    private static final double DEFAULT_LAT = 48.751911;
    private static final double DEFAULT_LONGI = -122.478683;

    private class ButtonHandler implements View.OnClickListener{

        public void onClick(View v){
            int buttonClicked = v.getId();
            if(buttonClicked == R.id.homebutton){
                home(v);
            }
            else if(buttonClicked == R.id.organizationbutton){
                organization(v);
            }
            else if(buttonClicked == R.id.inventorybutton){
                inventory(v);
            }

        }
    }

    /*home onClick function should finish the current view and go back to the home screen*/
    public void home(View view){
        this.finish();
        //animation should come from left to right because organization is farthest right
        overridePendingTransition(R.anim.left_to_right,R.anim.left_to_right);

    }

    /*organization onClick function should do nothing*/
    public void organization(View view){
        return;
    }

    /*inventory onClick function should make the inventory_activity the current view*/
    public void inventory(View view){
        this.finish();
        Intent myIntent = new Intent(this, inventoryActivity.class);
        this.startActivity(myIntent);
        //animation should come from left to right because organization is farthest right
        overridePendingTransition(R.anim.left_to_right,R.anim.left_to_right);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ButtonHandler bh = new ButtonHandler();
        buttonAudio ba = new buttonAudio(this);
        Button home_button = (Button) findViewById(R.id.homebutton);
        home_button.setOnClickListener(bh);
        home_button.setOnTouchListener(ba);
        Button organization_button = (Button) findViewById(R.id.organizationbutton);
        organization_button.setOnClickListener(bh);
        organization_button.setOnTouchListener(ba);
        Button inventory_button = (Button) findViewById(R.id.inventorybutton);
        inventory_button.setOnClickListener(bh);
        inventory_button.setOnTouchListener(ba);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        double lat = DEFAULT_LAT;
        double longi = DEFAULT_LONGI;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                lat = locationGPS.getLatitude();
                longi = locationGPS.getLongitude();
                LatLng selfGPS = new LatLng(lat, longi);
                selfGPS = new LatLng(lat, longi);
            }
        }

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
        if (selfGPS != null) {
            mMap.addMarker(new MarkerOptions().position(selfGPS).title("Current position").icon(BitmapDescriptorFactory.defaultMarker(240)));
        }
        for (int i = 0; i < charList.size(); i++) {

            Charity curr = charList.get(i);
            LatLng gps = new LatLng(curr.getLatitude(), curr.getLongitude());
            mMap.addMarker(new MarkerOptions().position(gps).title(curr.getName()));
        }

        //mMap.addMarker(new MarkerOptions().position(Bham).title("Bham marker!!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Bham, 13f));
    }
}