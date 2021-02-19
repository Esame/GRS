package com.example.gettingridofstuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
/*MainActivity handles all actions in activity_main.xml*/
public class MainActivity extends AppCompatActivity {
    static DatabaseManager db;
    private AlertDialog.Builder popupBuilder;
    private AlertDialog popup;
    private boolean clothes = false;
    private boolean food = false;
    private boolean homeGoods = false;
    private boolean electronics = false;
    private boolean tools = false;
    private boolean vehicles = false;
    private boolean building = false;
    private boolean houseCare = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButtonHandler bh = new ButtonHandler();
        Button home_button = (Button) findViewById(R.id.homebutton);
        home_button.setOnClickListener(bh);
        Button organization_button = (Button) findViewById(R.id.organizationbutton);
        organization_button.setOnClickListener(bh);
        Button inventory_button = (Button) findViewById(R.id.inventorybutton);
        inventory_button.setOnClickListener(bh);

        //Create static donation center database when app is first opened
        //charityDatabase();

    }
   /* protected void onStart(){
        super.onStart();
        charityDatabase();
    }*/

    /*home onclick function should do nothing*/
    public void home(View view){
        return;
    }
    /*organization onClick function should make the activity_organization the current view*/
    public void organization(View view){
        Intent myIntent = new Intent(this, activity_organization.class);
        this.startActivity(myIntent);
        //charityDatabase();
        //animation should come from right to left because inventory is farthest right
        overridePendingTransition(R.anim.right_to_left, R.anim.right_to_left);

    }
    /*inventory onClick function should make the inventory_activity the current view*/
    public void inventory(View view){
        Intent myIntent = new Intent(this, inventoryActivity.class);
        this.startActivity(myIntent);
        //animation should come from left to right because organization is farthest right
        overridePendingTransition(R.anim.left_to_right,R.anim.left_to_right);

    }
    /*onClick for nav button unimplemented*/
    public void navigation(View v){

    }
    /*onClick for search button unimplemented*/
    public void search(View v){

    }
    /*onClick for filter button unimplemented*/
    /*public void filter(View v){

    }*/
    public void filter(View v){
        popupBuilder = new AlertDialog.Builder(this);
        final View filterPopupView = getLayoutInflater().inflate(R.layout.filter_popup, null);

        Button clothes_btn = filterPopupView.findViewById(R.id.clothes_btn);
        clothes_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(clothes){
                    clothes = false;
                }else {
                    clothes = true;
                }
            }

        });
        Button food_btn = filterPopupView.findViewById(R.id.food_btn);
        food_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(food){
                    food = false;
                }
                else {
                    food = true;
                }
            }
        });
        Button homeGoods_btn = filterPopupView.findViewById(R.id.home_goods_btn);
        homeGoods_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(homeGoods){
                    homeGoods = false;
                }
                else {
                    homeGoods = true;
                }
            }
        });
        Button electronics_btn = filterPopupView.findViewById(R.id.electronics_btn);
        electronics_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(electronics){
                    electronics = false;
                }
                else {
                    electronics = true;
                }
            }
        });Button tools_btn = filterPopupView.findViewById(R.id.tools_btn);
        tools_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(tools){
                    tools = false;
                }
                else {
                    tools = true;
                }
            }
        });Button vehicle_btn = filterPopupView.findViewById(R.id.vehicles_btn);
        vehicle_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(vehicles){
                    vehicles = false;
                }
                else {
                    vehicles = true;
                }
            }
        });Button building_btn = filterPopupView.findViewById(R.id.building_btn);
        building_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(building){
                    building = false;
                }
                else {
                    building = true;
                }
            }
        });Button houseCare_btn = filterPopupView.findViewById(R.id.house_care_btn);
        houseCare_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if(houseCare){
                    houseCare = false;
                }
                else {
                    houseCare = true;
                }
            }
        });
        Button cancel_btn = filterPopupView.findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                clothes = false;
                food = false;
                homeGoods = false;
                electronics = false;
                tools = false;
                vehicles = false;
                building = false;
                houseCare = false;
                popup.dismiss();
            }
        });
        Button confirm_btn = filterPopupView.findViewById(R.id.confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                popup.dismiss();
            }
        });

        popupBuilder.setView(filterPopupView);
        popup = popupBuilder.create();
        popup.show();
    }
    /*charityDatabase function manually placed Bellingham donation centers into a database */
    public void charityDatabase(){
        db = new DatabaseManager(this);
        //for each charity location, insert into database
        //name,hours,id,category,address

        Charity charity9 = new Charity("We Care of Whatcom County", "10am-3pm Sat - Tues", 0, "Clothing",  "3550 Meridian St Ste 2, Bellingham, WA 98225", 48.777988, -122.486023);
        Charity charity10 = new Charity("C4PIN\n" + "(Computers for people in need)", "9am-1pm Mon - Fri", 0, "Electronics", "2004 James St, Bellingham, WA 98225", 48.756520,-122.464930);
        Charity charity11 = new Charity("Value Village"," 11am - 7pm everyday",0, "Clothing, Homegoods, Electronics", "150 E Bellis Fair Pkwy, Bellingham, WA 98226",48.787340, -122.484080);
        Charity charity12 = new Charity("Habitat for Humanity in Whatcom County","9am - 6pm Tues-Fri 10am - 5pm Sat", 0, "Clothing, Homegoods, Electronics", "1825 Cornwall Ave, Bellingham, WA 98225",48.754600,-122.475160);
        Charity charity13 = new Charity("Lydia Place", "9am - 5pm Mon-Fri", 0, "Clothing, Homegoods, Electronics, Vehicles, Tools, House care supplies", "1701 Gladstone St, Bellingham, WA 98229", 48.7491207, -122.453715);
        Charity charity14 = new Charity("Trash to Treasures Thrift Store", "10am - 7pm Weds - Fri, 9 am - 7pm Sat, 9 am - 7pm Sun", 0, "Clothing", "436 W Bakerview Rd STE 112, Bellingham, WA 98226", 48.7898378,-122.4953422);
        Charity charity15 = new Charity("Labels Women’s Consignment", "10am - 6pm Wed - Sat\n" + "11am - 5pm Sun", 0,"Clothing, Homegoods","2332 James St, Bellingham, WA 98225",48.7608667,-122.4647976);
        Charity charity1 = new Charity("Goodwill Bellingham" , "10am - 8pm everyday" , 0, "Clothing, Homegoods, Food, Electronics", "1115 E Sunset Dr, Bellingham, WA 98226", 48.771930, -122.461120);
        Charity charity2 = new Charity("Lighthouse Mission Ministries", "9am-4pm Mon - Fri", 0, "Clothing, Homegoods, Food, Electronics", "910 W Holly St Bellingham, WA 98225", 48.755060,-122.487250 );
        Charity charity3 = new Charity("Ragfinery", "10am - 5pm Fri - Sun", 0, "Clothing", "1421 N Forest St, Bellingham, WA 98225",48.749620, -122.473870 );
        Charity charity4 = new Charity("Wise Buys Thrift Store", "Closed for COVID", 0, "Clothing, Homegoods, Electronic", "1224 N State St, Bellingham, WA 98225", 48.747920, -122.477760);
        Charity charity5 = new Charity("Assistance League of Bellingham, Thrift & Gift Shop", "Closed for COVID", 0, "Clothes, Homegoods", "2817 Meridian Street, Bellingham, WA 98225", 48.739123199999995, -122.4802304);
        Charity charity6 = new Charity("Bellingham Food Bank", "3pm - 6pm Wed, 1pm - 4pm Tues and Fri", 0, "Food", "1824 Ellis St, Bellingham, WA 98225", 48.754700, -122.471770);
        Charity charity7 = new Charity("Worn Again Thrift", "11am - 6pm Tues - Sun", 0, "Clothing", "232 E Champion St, Bellingham, WA 98225",48.7504438, -122.4744212 );
        Charity charity8 = new Charity("The RE Store", "11am - 5pm, Tues - Sat", 0, "Tools, Building materials, House care supplies", "2309 Meridian Street, Bellingham, WA 98225", 48.761594, -122.48646);

        db.insert(charity1);
        db.insert(charity2);
        db.insert(charity3);
        db.insert(charity4);
        db.insert(charity5);
        db.insert(charity6);
        db.insert(charity7);
        db.insert(charity8);
        db.insert(charity9);
        db.insert(charity10);
        db.insert(charity11);
        db.insert(charity12);
        db.insert(charity13);
        db.insert(charity14);
        db.insert(charity15);

    }
    /*ButtonHandler function is used to listen to buttons in the header and change their functionality*/
    private class ButtonHandler implements View.OnClickListener {

        public void onClick(View v) {
            int buttonClicked = v.getId();
            if (buttonClicked == R.id.homebutton) {
                home(v);
            } else if (buttonClicked == R.id.organizationbutton) {
                organization(v);
            } else if (buttonClicked == R.id.inventorybutton) {
                inventory(v);
            }

        }
    }

}