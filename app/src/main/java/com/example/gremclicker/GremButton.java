package com.example.gremclicker;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

class GremButton {

    static AnimationDrawable gremAnimation;
    static int currentFrame;
    @SuppressLint("ClickableViewAccessibility")
    public GremButton(ImageView iv) {
        iv.setBackgroundResource(R.drawable.animated_grem);
        gremAnimation = (AnimationDrawable) iv.getBackground();
        iv.setOnTouchListener(new customOnClickListener());
    }
}

class customOnClickListener implements View.OnTouchListener {
    static int currentFrame = 0;
    static int frameAdvance(int currentFrame) {
        if (currentFrame + 1 > 12) {
            return 0;
        } else {
            Log.e("heeelp",String.valueOf(currentFrame));
            return currentFrame + 1;
        }
    }

    //TODO fix drag crash with motionevent and fix touchup and touchdown (dupe)
    // https://stackoverflow.com/questions/8973148/how-to-get-click-position-in-onclicklistener
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Bitmap b = getBitmapFromView(view);
        int color = b.getPixel((int) event.getX(), (int) event.getY());
        if (color == Color.TRANSPARENT) {
            currentFrame = frameAdvance(currentFrame);
            ((AnimationDrawable) view.getBackground()).selectDrawable(currentFrame);
            return true;
        }
        return false;
    }
    Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}

