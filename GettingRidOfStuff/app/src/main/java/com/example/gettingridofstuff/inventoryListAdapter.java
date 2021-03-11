package com.example.gettingridofstuff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class inventoryListAdapter extends ArrayAdapter<inventoryItem> {

    ArrayList<inventoryItem> itemList;
    Context ctx;

    public inventoryListAdapter(Context context, ArrayList<inventoryItem> list){
        super(context, 0, list);
        itemList = list;
        ctx = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //add item info here
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inventory_card, parent, false);
        }
        inventoryItem item = getItem(position);

        TextView title = convertView.findViewById(R.id.item_card_title);
        title.setText(item.label + " × " + Integer.toString(item.quantity));

        TextView desc = convertView.findViewById(R.id.item_card_description);
        desc.setText(item.type);


        buttonAudio bDel = new buttonAudio(ctx, false);
        Button delete = convertView.findViewById(R.id.item_card_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                itemList.remove(item);
                notifyDataSetChanged();
            }
        });
        delete.setOnTouchListener(bDel);

        return convertView;
    }
}
