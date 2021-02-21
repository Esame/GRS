package com.example.gettingridofstuff;

import android.view.View;

public class inventoryItem {
    public String label;
    public String type;
    public int quantity;

    public inventoryItem(String _label, String _type, int _quantity){
        this.label = _label;
        this.type = _type;
        this.quantity = _quantity;
    }

    public inventoryItem(String loadString){
        String[] elements = loadString.split("\n");
        label = elements[0];
        type = elements[1];
        quantity = Integer.parseInt(elements[2]);
    }

    public String saveString(){
        return label + "\n" + type + "\n" + quantity;
    }
}
