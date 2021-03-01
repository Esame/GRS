package com.example.gettingridofstuff;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class buttonAudio implements View.OnTouchListener{

    static MediaPlayer clickDown;
    static MediaPlayer clickUp;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        buttonClickAudio(event);
        return false;
    }

    public buttonAudio(Context ctx){
        clickDown = MediaPlayer.create(ctx, R.raw.click_down);
        clickUp = MediaPlayer.create(ctx, R.raw.click_up);
    }

    public static void buttonClickAudio(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            clickDown.start();
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            clickUp.start();
        }
    }
}