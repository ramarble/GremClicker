package com.example.gremclicker

import android.annotation.SuppressLint
import android.app.usage.UsageEvents
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Surface
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.drawToBitmap

class MainActivity<Bitmap> : AppCompatActivity() {


    private var currentFrame: Int = 1
    private var globalCounter: Int = 0;
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gremAnimation: AnimationDrawable
        var grem: ImageView = findViewById<ImageView?>(R.id.animatedGrem).apply {
            setBackgroundResource(R.drawable.animated_grem)
            gremAnimation = background as AnimationDrawable;
        }
        var num = findViewById<TextView>(R.id.textView)

        //move this outside a lambda so it's a readable function
        //code that makes the grem groove and walk
        grem.setOnTouchListener() { p0, p1 ->
            var b: android.graphics.Bitmap = p0!!.drawToBitmap()
            var i: Int = b.getColor(p1!!.x.toInt(), p1.y.toInt()).toArgb()
            if (i != Color.TRANSPARENT) {
                p0.performClick();
                frameAdvance(currentFrame)
                gremAnimation.selectDrawable(currentFrame);
                num.text = currentFrame.toString();
                true;
            }
            false;
        }
    }


    private fun frameAdvance(currentFrame:Int) {
            if (currentFrame + 1 > 12) {
                this.currentFrame = 0
            } else {
                ++this.currentFrame;
            }
    }

}