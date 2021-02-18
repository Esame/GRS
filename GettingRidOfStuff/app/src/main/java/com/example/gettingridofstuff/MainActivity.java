package com.example.gettingridofstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*MainActivity handles all actions in activity_main.xml*/
public class MainActivity extends AppCompatActivity {
    static DatabaseManager db;
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
        charityDatabase();

    }
    /*home onclick function should do nothing*/
    public void home(View view){
        return;
    }
    /*organization onClick function should make the activity_organization the current view*/
    public void organization(View view){
        Intent myIntent = new Intent(this, activity_organization.class);
        this.startActivity(myIntent);
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
    public void filter(View v){

    }
    public void charityDatabase(){
        db = new DatabaseManager(this);
        //for each charity location, insert into database
        //name,hours,id,category,address
        Charity charity1 = new Charity("Goodwill Bellingham" , "10am - 8pm everyday" , "0", "Clothing, Homegoods, Food, Electronics", "1115 E Sunset Dr, Bellingham, WA 98226");
        Charity charity2 = new Charity("Lighthouse Mission Ministries", "9am-4pm Mon - Fri", "0", "Clothing, Homegoods, Food, Electronics", "910 W Holly St Bellingham, WA 98225");
        Charity charity3 = new Charity("Ragfinery", "10am - 5pm Fri - Sun", "0", "Clothing", "1421 N Forest St, Bellingham, WA 98225" );
        Charity charity4 = new Charity("Wise Buys Thrift Store", "Closed for COVID", "0", "Clothing, Homegoods, Electronic", "1224 N State St, Bellingham, WA 98225");
        Charity charity5 = new Charity("Assistance League of Bellingham, Thrift & Gift Shop", "Closed for COVID", "0", "Clothes, Homegoods", "2817 Meridian Street, Bellingham, WA 98225");
        Charity charity6 = new Charity("Bellingham Food Bank", "3pm - 6pm Wed, 1pm - 4pm Tues and Fri", "0", "Food", "1824 Ellis St, Bellingham, WA 98225");
        Charity charity7 = new Charity("Worn Again Thrift", "11am - 6pm Tues - Sun", "0", "Clothing", "232 E Champion St, Bellingham, WA 98225");
        Charity charity8 = new Charity("The RE Store", "11am - 5pm, Tues - Sat", "0", "Tools, Building materials, House care supplies", "2309 Meridian Street, Bellingham, WA 98225");

        db.insert(charity1);
        db.insert(charity2);
        db.insert(charity3);
        db.insert(charity4);
        db.insert(charity5);
        db.insert(charity6);
        db.insert(charity7);
        db.insert(charity8);
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