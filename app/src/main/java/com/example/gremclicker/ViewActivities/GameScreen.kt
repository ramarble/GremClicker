package com.example.gremclicker.ViewActivities

import android.animation.ObjectAnimator
import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.Currency.Currency.Companion.currency
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database
import com.example.gremclicker.ButtonLogic.*

class GameScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grem_button_main)
        val animatedGrem = findViewById<ImageView?>(R.id.animatedGrem)
        val gremButton = GremButton(animatedGrem);
        fakeFrameUpdate()
        val Object = getIntent().getSerializableExtra("User")
        Toast.makeText(this, Object.toString(), Toast.LENGTH_SHORT).show()
        //createOtomo()
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

fun createOtomo() {

    var iv = ImageView(this)
    var lp = LayoutParams(200, LayoutParams.MATCH_PARENT)
    iv.setImageResource(R.drawable.otomo2);

    addContentView(iv, lp);
    var animation = ObjectAnimator.ofFloat(iv, "translationY", 13000f).setDuration(10000)
    var rot = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f)
    rot.repeatCount = 1000;
    rot.start()
    animation.start()
    iv.setOnClickListener { animation.cancel(); rot.cancel() }


}
}