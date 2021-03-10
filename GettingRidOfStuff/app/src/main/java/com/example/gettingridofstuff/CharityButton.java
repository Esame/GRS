package com.example.gettingridofstuff;
import android.content.Context;

public class CharityButton extends androidx.appcompat.widget.AppCompatButton {
    private Charity charity;

    public CharityButton(Context context, Charity charity ) {
        super( context );
        charity = this.charity;
    }
    
}
