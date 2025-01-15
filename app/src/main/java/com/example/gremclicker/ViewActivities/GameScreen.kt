package com.example.gremclicker.ViewActivities

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.Currency.Currency
import com.example.gremclicker.Currency.Currency.Companion.currency
import com.example.gremclicker.R
import com.example.gremclicker.Logic.*
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database
import java.security.SecureRandom

class GameScreen: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grem_button_main)
        val User: DB_User = getIntent().getSerializableExtra("User") as DB_User
        val dbLoader = Database(baseContext)

        currency = User.grems

        val animatedGrem = findViewById<ImageView?>(R.id.animatedGrem)
        GremButton(animatedGrem);

        var CURRENT_POINTS: TextView = findViewById(R.id.currencyDisplay)
        fakeFrameUpdate(CURRENT_POINTS)


        Toast.makeText(this, User.toString(), Toast.LENGTH_SHORT).show()
        randomOtomoRain(8000)

        var saveButton: TextView = this.findViewById(R.id.buttonSaveData)
        saveButton.setTextSize(10f)

        saveButton.setOnClickListener{
            dbLoader.UPDATE_USER(User.name, Integer.parseInt(CURRENT_POINTS.text.toString()), dbLoader.writableDatabase)
            Toast.makeText(this, "idk?", Toast.LENGTH_SHORT).show()
        }

    }

    fun fakeFrameUpdate(points: TextView) {
        Thread(Runnable {
            while (true) {
                runOnUiThread {
                    points.text = currency.toString()
                }
                Thread.sleep(20)
            }
        }).start()
    }

    fun randomOtomoRain(int: Int) {
        Thread(Runnable {
            while (true) {
                runOnUiThread {
                    FallingOtomo.createFallingOtomo(this)
                }
                Thread.sleep(SecureRandom().nextInt(int).toLong())
            }
        }).start()
    }
}