package com.example.gremclicker.CustomListeners;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;

import com.example.gremclicker.Currency.Currency;

public class customOnClickListener implements View.OnTouchListener {
    static int currentFrame = 0;
    static int frameAdvance(int currentFrame) {
        if (currentFrame + 1 > 12) {
            return 0;
        } else {
            return currentFrame + 1;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Bitmap b = getBitmapFromView(view);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int color = b.getPixel((int) event.getX(), (int) event.getY());
            if (color != Color.TRANSPARENT) {
                currentFrame = frameAdvance(currentFrame);
                ((AnimationDrawable) view.getBackground()).selectDrawable(currentFrame);

                Currency.Companion.addCurrency(1);

                return true;
            }
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

