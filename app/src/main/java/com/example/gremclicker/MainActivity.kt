package com.example.gremclicker

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gremclicker.ButtonLogic.GremButton
import com.example.gremclicker.Currency.Currency.Companion.currency
import android.view.ViewGroup.LayoutParams
import android.view.animation.Animation
import android.view.animation.AnimationSet
import androidx.core.animation.addListener
import androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility", "SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.grem_button_main)

        val animatedGrem = findViewById<ImageView?>(R.id.animatedGrem)
        val gremButton = GremButton(animatedGrem);

        fakeFrameUpdate()

        //DO THIS WITH A FAKE THING
        createOtomo()
    }

    fun fakeFrameUpdate() {
        Thread(Runnable {
            while (true) {
                runOnUiThread({
                    findViewById<TextView>(R.id.currencyDisplay).text = currency.toString()
                })
                Thread.sleep(20)
            }
        }).start()
    }

    @SuppressLint("ResourceType", "Recycle")
    fun createOtomo() {

        var iv = ImageView(this)
        var lp = LayoutParams(200, LayoutParams.MATCH_PARENT)
        iv.setImageResource(R.drawable.otomo2);

        this.addContentView(iv, lp);
        var animation = ObjectAnimator.ofFloat(iv, "translationY", 13000f).setDuration(10000)
        var rot = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f)
        rot.repeatCount = 1000;
        rot.start()
        animation.start()
        iv.setOnClickListener { animation.cancel(); rot.cancel() }


        }
    }