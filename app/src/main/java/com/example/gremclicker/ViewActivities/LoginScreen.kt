package com.example.gremclicker.ViewActivities

import android.app.Activity
import android.widget.Button
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.Database

class LoginScreen(a: Activity){

    var a: Activity = a;
    val dbLoader = Database(a.baseContext)
    val db = dbLoader.writableDatabase

    fun main() {

        a.setContentView(R.layout.login)

        val b = a.findViewById<Button>(R.id.button)
        b.setOnClickListener {
            dbLoader.newUser("hi", db)
        }

        val LoadPlayerButton = a.findViewById<Button>(R.id.loadplayerbutton)
        LoadPlayerButton.setOnClickListener {
            LoadUserScreen(a, dbLoader).main()
        }
    }
}
