package com.example.gettingridofstuff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    public void home(View view){
        return;
        //animation should come from right to left because inventory is farthest right
        //overridePendingTransition(to left enter, to left exit);


    }
    public void organization(View view){
        Intent myIntent = new Intent(this, activity_organization.class);
        this.startActivity(myIntent);
        //animation should come from right to left because inventory is farthest right
        //overridePendingTransition(to left enter, to left exit);
    }
    public void inventory(View view){
        Intent myIntent = new Intent(this, inventoryActivity.class);
        this.startActivity(myIntent);
        //animation should come from left to right because organization is farthest right
        //overridePendingTransition(to right enter,to right enter);

    }
    public void navigation(View v){

    }
    public void search(View v){

    }
    public void filter(View v){

    }
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