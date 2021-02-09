package com.example.gettingridofstuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/*inventoryActivity handles all actions in the activity_inventory.xml*/
public class inventoryActivity extends AppCompatActivity {

    private AlertDialog.Builder popupBuilder;
    private AlertDialog popup;
    private Button addItemButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        ButtonHandler bh = new ButtonHandler();
        Button home_button = (Button) findViewById(R.id.homebutton);
        home_button.setOnClickListener(bh);
        Button organization_button = (Button) findViewById(R.id.organizationbutton);
        organization_button.setOnClickListener(bh);
        Button inventory_button = (Button) findViewById(R.id.inventorybutton);
        inventory_button.setOnClickListener(bh);

        addItemButton = findViewById(R.id.btn_inventory_add);

        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                createPopup();
            }
        });
    }

    /*Create popup will bring up inventor_popup upon a button click*/
    public void createPopup(){
        popupBuilder = new AlertDialog.Builder(this);
        final View itemPopupView = getLayoutInflater().inflate(R.layout.inventory_popup, null);

        //grab fields here

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