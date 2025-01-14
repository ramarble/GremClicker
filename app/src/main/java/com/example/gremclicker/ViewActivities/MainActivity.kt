package com.example.gremclicker.ViewActivities

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.Currency.Currency.Companion.currency
import com.example.gremclicker.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        LoginScreen(this).main()
    }
}