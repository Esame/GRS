package com.example.gettingridofstuff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
/*

DEPRECATED, NOT IN USE


 */
/*activity_organization handles all actions in organizations.xml*/
public class activity_organization extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;

    private GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization);
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
        Button map_button = (Button) findViewById(R.id.button_1);
        map_button.setOnTouchListener(ba);

        //map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

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

    public void realMapView(View view) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double lat = -1;
            double longi = -1;
            if (locationGPS != null) {
                lat = locationGPS.getLatitude();
                longi = locationGPS.getLongitude();
                LatLng selfGPS = new LatLng(lat, longi);
            }
            this.finish();
            Intent myIntent = new Intent(this, MapsActivity.class);
            myIntent.putExtra("lat", lat);
            myIntent.putExtra("longi", longi);
            this.startActivity(myIntent);
        }
    }

    /*ButtonHandler function is used to listen to buttons in the header and change their functionality*/
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
}
