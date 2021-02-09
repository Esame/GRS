package com.example.gettingridofstuff;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class inventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        activity_organization.ButtonHandler bh = new activity_organization.ButtonHandler();
        Button home_button = (Button) findViewById(R.id.homebutton);
        homebutton.setOnClickListener(bh);
        Button organization_button = (Button) findViewById(R.id.organizationbutton);
        organizationbutton.setOnClickListener(bh);
        Button inventory_button = (Button) findViewById(R.id.inventorybutton);
        inventorybutton.setOnClickListener(bh);

    }
    public void home(View view){
        return;
        //animation should come from right to left because inventory is farthest right
        //overridePendingTransition(to left enter, to left exit);
    }

    public void organization(View view){
        this.finish();
        Intent myIntent = new Intent(this, activity_organization.class);
        this.startActivity(myIntent);
        //animation should come from right to left because inventory is farthest right
        //overridePendingTransition(to left enter, to left exit);
    }

    public void inventory(View view){
        this.finish();
        Intent myIntent = new Intent(this, inventoryActivity.class);
        this.startActivity(myIntent);
        //animation should come from left to right because organization is farthest right
        //overridePendingTransition(to right enter,to right enter);
    }
    private class ButtonHandler implements View.OnClickListener{

        public void onClick(View v){
            int buttonClicked = v.getId();
            if(buttonClicked == findViewById(R.id.homebutton)){
                home(v);
            }
            else if(buttonClicked == findViewById(R.id.organizationbutton)){
                organization(v);
            }
            else if(buttonClicked == findViewById(R.id.inventorybutton)){
                inventory(v);
            }

        }
    }
}