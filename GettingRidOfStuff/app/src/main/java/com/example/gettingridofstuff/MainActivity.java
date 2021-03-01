package com.example.gettingridofstuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import java.util.Scanner;

import java.util.ArrayList;

/*MainActivity handles all actions in activity_main.xml*/
public class MainActivity extends AppCompatActivity {
    Scanner scanner = new Scanner(System.in);
    static DatabaseManager db;
    private AlertDialog.Builder popupBuilder;
    private AlertDialog popup;
    AutoCompleteTextView search_bar;
    ArrayList<String> charity_names = new ArrayList<>();
    ArrayList<Charity> charities = new ArrayList<>();
    private ScrollView scrollView;
    ArrayList<String> filters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("GRS-DEBUG", "STARTING");
        scrollView = findViewById( R.id.scrollView );
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

        //Create static donation center database when app is first opened
        db = new DatabaseManager(this, this);

        charities = db.selectAll( );


    }
   protected void onStart(){
        super.onStart();
        update();
   }

   public void update(){
       scrollView.removeAllViewsInLayout();
       // set up the grid layout
       GridLayout grid = new GridLayout( this );
       grid.setRowCount(15);
       grid.setColumnCount( 1 );

       // create array of buttons, 2 per row
       CharityButton [] buttons = new CharityButton[15];
       ButtonHandler bh = new ButtonHandler( );

       // fill the grid
       int i = 0;
       for ( Charity charity: charities ) {
           // create the button
           buttons[i] = new CharityButton( this, charity );
           buttons[i].setText( (charity.getName( )
                   + "\n" + charity.getHours( )) );

           // set up event handling
           buttons[i].setOnClickListener( bh );

           // add the button to grid
           grid.addView( buttons[i], 500, GridLayout.LayoutParams.MATCH_PARENT);
           i++;
           if(i == 15){
               break;
           }
       }
       scrollView.addView( grid );

       for(Charity charity: charities){
           charity_names.add(charity.getName());
       }
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, charity_names);
       search_bar = findViewById(R.id.search_bar);
       search_bar.setAdapter(adapter);
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
        scrollView.removeAllViewsInLayout( );
        GridLayout grid = new GridLayout( this );
        String name = search_bar.getText().toString();

        CharityButton button;
        Charity none = new Charity("","",0,"", "",0, 0);
        Boolean found = false;

        for(Charity charity: charities){
            if(charity.getName().equals(name)){
                button = new CharityButton(this, charity);
                button.setText((charity.getName() + " " + charity.getHours()));
                found = true;
                grid.addView( button, 500, GridLayout.LayoutParams.MATCH_PARENT );
                break;
            }
        }
        if(!found){
            button = new CharityButton(this, none);
            button.setText("This place is not in our database");
            grid.addView( button, 500, GridLayout.LayoutParams.MATCH_PARENT );
        }
        scrollView.addView(grid);

    }
    /*onClick for filter button unimplemented*/
    /*public void filter(View v){

    }*/
    public void filter(View v){
        filters.clear();
        popupBuilder = new AlertDialog.Builder(this);
        final View filterPopupView = getLayoutInflater().inflate(R.layout.filter_popup, null);

        Button clothes_btn = filterPopupView.findViewById(R.id.clothes_btn);
        clothes_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("Clothing");
            }

        });
        Button food_btn = filterPopupView.findViewById(R.id.food_btn);
        food_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("food");
            }
        });
        Button homeGoods_btn = filterPopupView.findViewById(R.id.home_goods_btn);
        homeGoods_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("homeGoods");
            }
        });
        Button electronics_btn = filterPopupView.findViewById(R.id.electronics_btn);
        electronics_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("electronics");
            }
        });Button tools_btn = filterPopupView.findViewById(R.id.tools_btn);
        tools_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("tools");
            }
        });Button vehicle_btn = filterPopupView.findViewById(R.id.vehicles_btn);
        vehicle_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("vehicles");
            }
        });Button building_btn = filterPopupView.findViewById(R.id.building_btn);
        building_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("BuildingMaterials");
            }
        });Button houseCare_btn = filterPopupView.findViewById(R.id.house_care_btn);
        houseCare_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.add("HouseCareSupplies");
            }
        });
        Button cancel_btn = filterPopupView.findViewById(R.id.cancel);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                filters.clear();
                popup.dismiss();
            }
        });
        Button confirm_btn = filterPopupView.findViewById(R.id.confirm);
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                popup.dismiss();
                updateView();
            }
        });

        popupBuilder.setView(filterPopupView);
        popup = popupBuilder.create();
        popup.show();
    }
    /*charityDatabase function manually placed Bellingham donation centers into a database */
   // public void charityDatabase(){


    //}
    private void updateView(){
        scrollView.removeAllViewsInLayout( );
        GridLayout grid = new GridLayout( this );
        grid.setRowCount( 15);
        grid.setColumnCount( 1 );

        // create array of buttons, 2 per row
        CharityButton [] buttons = new CharityButton[15];
        ButtonHandler bh = new ButtonHandler( );

        // fill the grid
        int i = 0;
        for ( Charity charity: charities ) {
            // create the button
            for(String filter: filters) {
                String[] categories = charity.category.split(" ");
                for(String check : categories) {
                    if(i == 15){
                        break;
                    }
                    if (filter.equalsIgnoreCase(check) ) {

                        buttons[i] = new CharityButton(this, charity);
                        buttons[i].setText((charity.getName()
                                + "\n" + charity.getHours()));

                        // set up event handling
                        buttons[i].setOnClickListener(bh);

                        // add the button to grid
                        grid.addView(buttons[i], 500, GridLayout.LayoutParams.MATCH_PARENT);
                        i++;
                        break;
                    }

                }
            }
        }
        scrollView.addView( grid );
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
