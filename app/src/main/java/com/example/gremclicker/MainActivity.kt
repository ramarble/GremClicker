package com.example.gremclicker

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.ButtonLogic.GremButton
import com.example.gremclicker.Currency.Currency
import com.example.gremclicker.Currency.Currency.Companion.currency


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.grem_button_main)

        val animatedGrem = findViewById<ImageView?>(R.id.animatedGrem)
        val gremButton = GremButton(animatedGrem);

        Thread(Runnable {
            while (true) {
                runOnUiThread({
                    findViewById<TextView>(R.id.currencyDisplay).text = currency.toString()
                })
                Thread.sleep(20)
            }
        }).start()
    }
}