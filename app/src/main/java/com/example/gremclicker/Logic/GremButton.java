package com.example.gremclicker.Logic;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.example.gremclicker.CustomListeners.customOnClickListener;
import com.example.gremclicker.R;

public class GremButton {

    static AnimationDrawable gremAnimation;
    @SuppressLint("ClickableViewAccessibility")
    public GremButton(ImageView gremImage) {
        gremImage.setBackgroundResource(R.drawable.animated_grem);
        gremAnimation = (AnimationDrawable) gremImage.getBackground();
        customOnClickListener customClick = new customOnClickListener();
        gremImage.setOnTouchListener(customClick);
    }
}

