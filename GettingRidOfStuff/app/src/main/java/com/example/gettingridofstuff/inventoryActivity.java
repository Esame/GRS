package com.example.gettingridofstuff;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/*inventoryActivity handles all actions in the activity_inventory.xml*/
public class inventoryActivity extends AppCompatActivity {

    private AlertDialog.Builder popupBuilder;
    private AlertDialog popup;
    private Button addItemButton;
    private GridView itemGrid;
    ArrayList<inventoryItem> itemList;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

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

        //grab fields here
        Button cancelAddButton = itemPopupView.findViewById(R.id.btn_inventory_cancel);
        Button confirmAddButton = itemPopupView.findViewById(R.id.btn_inventory_add);
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