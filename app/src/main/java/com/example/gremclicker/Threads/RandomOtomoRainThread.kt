package com.example.gremclicker.Threads

import android.app.Activity
import com.example.gremclicker.Logic.FallingOtomo

class RandomOtomoRainThread(act: Activity, sleepTime: Int) : Runnable {

    @Volatile
    var runOtomoThread: Boolean = true

    var sleepTime: Int = sleepTime
    var act: Activity = act

    override fun run() {
        while (runOtomoThread) {
            act.runOnUiThread {
                FallingOtomo.createFallingOtomo(act)
            }
            Thread.sleep(sleepTime.toLong())
        }
    }
}