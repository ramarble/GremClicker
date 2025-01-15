package com.example.gremclicker.Threads

import android.app.Activity
import android.widget.TextView
import com.example.gremclicker.Currency.Currency
import com.example.gremclicker.R

class TextUpdateThread(act: Activity) : Runnable {

    var act: Activity = act
    override fun run() {
            while (true) {
                act.runOnUiThread {
                    act.findViewById<TextView>(R.id.currencyDisplay).text = Currency.getCurrency().toString()
                }
                Thread.sleep(20)
            }
    }
}