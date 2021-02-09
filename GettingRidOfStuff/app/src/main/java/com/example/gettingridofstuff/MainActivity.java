package com.example.gettingridofstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*MainActivity handles all actions in activity_main.xml*/
public class MainActivity extends AppCompatActivity {

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