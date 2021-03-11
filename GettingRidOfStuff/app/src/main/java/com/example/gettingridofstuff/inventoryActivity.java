package com.example.gettingridofstuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.String.join;

/*inventoryActivity handles all actions in the activity_inventory.xml*/
public class inventoryActivity extends AppCompatActivity {


    private AlertDialog.Builder popupBuilder;
    private AlertDialog popup;
    private Button addItemButton;
    private GridView itemGrid;
    ArrayList<inventoryItem> itemList;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private ScrollView scrollView;
    private String start_lat;
    private String start_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        realMapView();
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

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

        //adding listening to route button
        Button route_button = (Button) findViewById(R.id.btn_inventory_route);
        route_button.setOnClickListener(bh);


        addItemButton = findViewById(R.id.btn_inventory_add);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                createPopup();
            }
        });

        //set up grid
        itemGrid = findViewById(R.id.inventory_grid);

        load();
        itemGrid.setAdapter(new inventoryListAdapter(this, itemList));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    //saves the inventory items to a string set in storedPreferences
    private void save(){
        Set<String> items = new HashSet<String>();
        for(int i = 0; i < itemList.size(); i++){
            items.add(itemList.get(i).saveString());
        }
        editor.putStringSet("INVENTORY", items);
        editor.apply();
    }

    //loads the inventory items from a string set in storedPreferences
    private void load(){
        Set<String> items = sharedPref.getStringSet("INVENTORY", null);

        itemList = new ArrayList<inventoryItem>();

        if(items != null){
            for(String save : items){
                itemList.add(new inventoryItem(save));
            }
        }
    }

    /*Create popup will bring up inventor_popup upon a button click*/
    public void createPopup(){
        popupBuilder = new AlertDialog.Builder(this);
        final View itemPopupView = getLayoutInflater().inflate(R.layout.inventory_popup, null);

        buttonAudio bAdd = new buttonAudio(this, true);

        //grab fields here
        Button cancelAddButton = itemPopupView.findViewById(R.id.btn_inventory_cancel);
        Button confirmAddButton = itemPopupView.findViewById(R.id.btn_inventory_add);
        confirmAddButton.setOnTouchListener(bAdd);
        Spinner categorySpinner = itemPopupView.findViewById(R.id.category_spinner);
        EditText nameField = itemPopupView.findViewById(R.id.item_name_input);
        EditText quantity = itemPopupView.findViewById(R.id.item_quantity_input);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        cancelAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                popup.dismiss();
            }
        });

        confirmAddButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String name = nameField.getText().toString();
                String quantityString = quantity.getText().toString();
                if(name.matches("") || quantityString.matches("")) {
                    //prompt valid input
                }else {
                    inventoryItem itm = new inventoryItem(name, categorySpinner.getSelectedItem().toString(), Integer.parseInt(quantityString));
                    itemList.add(itm);
                }
                popup.dismiss();
            }
        });

        popupBuilder.setView(itemPopupView);
        popup = popupBuilder.create();
        popup.show();
    }

    /*home onclick function should display the home screen view*/
    public void home(View view){
        this.finish();
        //animation should come from right to left because inventory is farthest right
        overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left);
    }

    /*organization onClick function should make the activity_organization the current view*/
    public void organization(View view){
        this.finish();
        Intent myIntent = new Intent(this, activity_organization.class);
        this.startActivity(myIntent);
        //animation should come from right to left because inventory is farthest right
        overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left);
    }

    /*inventory onClick function should do nothin*/
    public void inventory(View view){
        //should do nothing because it is the current screen
        return;
    }

    /*Function to handle event when route button is clicked by user */
    public void route(View view){
        popupBuilder = new AlertDialog.Builder(this);
        final View routePopupView = getLayoutInflater().inflate(R.layout.route_popup, null);

        //get item list, use to compare categories w database
        boolean categories[];

        //Shows which categories the user's items belong in
        categories = new boolean[8];

        for(int i = 0; i < itemList.size(); i++){
            if(itemList.get(i).type.equals("Clothing")){
                categories[0] = true;
            }
            if(itemList.get(i).type.equals("Electronics")){
                categories[1] = true;
            }
            if(itemList.get(i).type.equals("Homegoods")){
                categories[2] = true;
            }
            if(itemList.get(i).type.equals("Vehicles")){
                categories[3] = true;
            }
            if(itemList.get(i).type.equals("Tools")){
                categories[4] = true;
            }
            if(itemList.get(i).type.equals("Food")){
                categories[5] = true;
            }
            if(itemList.get(i).type.equals("HouseCareSupplies")){
                categories[6] = true;
            }
            if(itemList.get(i).type.equals("BuildingMaterials")){
                categories[7] = true;
            }

        }
        ArrayList<Charity> matchedCharities= new ArrayList<>();
        ArrayList<Charity> allCharities = MainActivity.db.selectAll();

        System.out.println("Our categories array is " + categories[0] + categories[1]+ categories[2]+ categories[3]+ categories[4]+ categories[5]+ categories[6]+ categories[7]);
        for(int j = 0; j < allCharities.size(); j++){
            System.out.println("Cur charity category" + allCharities.get(j).getCategory());
            if(categories[0] && allCharities.get(j).getCategory().contains("Clothing")){
                matchedCharities.add(allCharities.get(j));
                System.out.println("is saying category is in");
            }
            if(categories[1] && allCharities.get(j).getCategory().contains("Electronics")){
                matchedCharities.add(allCharities.get(j));
            }
            if(categories[2] && allCharities.get(j).getCategory().contains("Homegoods")){
                matchedCharities.add(allCharities.get(j));
            }
            if(categories[3] && allCharities.get(j).getCategory().contains("Vehicles")){
                matchedCharities.add(allCharities.get(j));
            }
            if(categories[4] && allCharities.get(j).getCategory().contains("Tools")){
                matchedCharities.add(allCharities.get(j));
            }
            if(categories[5] && allCharities.get(j).getCategory().contains("Food")){
                matchedCharities.add(allCharities.get(j));
            }
            if(categories[6] && allCharities.get(j).getCategory().contains("HouseCareSupplies")){
                matchedCharities.add(allCharities.get(j));
            }
            if(categories[7] && allCharities.get(j).getCategory().contains("BuildingMaterials")){
                matchedCharities.add(allCharities.get(j));
            }
        }
        scrollView = routePopupView.findViewById( R.id.routeScrollView );
        GridLayout grid = new GridLayout(this);
        grid.setRowCount(15);
        grid.setColumnCount( 1 );



        for(int k = 0; k < matchedCharities.size(); k++){
            System.out.println("Button added to grid view");
            CharityButton button = new CharityButton(this, matchedCharities.get(k));
            button.setText((matchedCharities.get(k).getName() + " " + matchedCharities.get(k).getHours()) + " " + matchedCharities.get(k).getAddress());
            grid.addView( button);
            String address1 = matchedCharities.get(k).getAddress();
            //String[] address = location.split(" ");
            //String address = join("+", list);
            String address = address1.replace(" ","+");
            String dest_lat = String.valueOf(matchedCharities.get(k).getLatitude());
            String dest_long = String.valueOf(matchedCharities.get(k).getLongitude());

            System.out.println(address);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" + start_lat +","+ start_long + "&destination=" + dest_lat + "," + dest_long));
                    startActivity(intent);
                }
            });
        }
        scrollView.addView( grid );
        Button exit_btn = routePopupView.findViewById(R.id.exit_button);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                popup.dismiss();
            }
        });


        popupBuilder.setView(routePopupView);
        popup = popupBuilder.create();
        popup.show();
    }
    public void realMapView() {
        Location locationGPS = null;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            double lat = -1;
            double longi = -1;

            this.finish();
            Intent myIntent = new Intent(this, MapsActivity.class);
            myIntent.putExtra("lat", lat);
            myIntent.putExtra("longi", longi);
            this.startActivity(myIntent);
        }
        start_lat = "48.733843";
        start_long = "-122.48647";
        if (locationGPS != null) {
            double latitude = locationGPS.getLatitude();
            double longitude = locationGPS.getLongitude();
            start_lat = String.valueOf(latitude);
            start_long = String.valueOf(longitude);
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
            else if(buttonClicked == R.id.btn_inventory_route){
                route(v);
            }

        }
    }
}