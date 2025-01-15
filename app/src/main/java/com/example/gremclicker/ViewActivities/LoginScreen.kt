package com.example.gremclicker.ViewActivities

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.text.InputType
import android.util.DisplayMetrics
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.Logic.MathClass
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database


class LoginScreen(a: Activity){

    var act: Activity = a;
    val dbLoader = Database(a.baseContext)

    fun main() {

        act.setContentView(R.layout.login)
        dbLoader.RECREATE_DATABASE(dbLoader.writableDatabase)

        var text: TextView = act.findViewById(R.id.gremClickerTitle)
        text.setTextSize(30f)

        //DEBUG TO TEST THAT THE BOOLEAN ARRAY WORKS
        var v: BooleanArray = MathClass.returnUpgradesAsBooleanArray(61)

        val newUserButton = act.findViewById<Button>(R.id.newUserButton)
        newUserButton.setOnClickListener {
            newUser(act,newUserButton)
        }

        val LoadPlayerButton = act.findViewById<Button>(R.id.loadplayerbutton)
        LoadPlayerButton.setOnClickListener {
            //DO THIS WITH AN INTENT
            var intent = Intent(act.baseContext, LoadUserScreen::class.java)
            act.startActivity(intent)

        }
    }

    fun newUser(act: Activity, b: Button) {
        b.text = "Start Game"
        //delete the clicklistener asap to avoid issues with race conditions
        b.setOnClickListener( null)
        var newTextBox = createTextBox()
        //I just took this code from somewhere else
        newTextBox.requestFocus()
        val inputMethodManager: InputMethodManager = act.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(newTextBox, 0)

        b.setOnClickListener {
            userSendConfirmation(newTextBox)
        }

    }
    fun createTextBox() : EditText {
        var newTextBox = EditText(act)
        newTextBox.inputType = InputType.TYPE_CLASS_TEXT
        newTextBox.minimumWidth = 300
        newTextBox.width = 300;

        var metrics = DisplayMetrics()
        act.windowManager.defaultDisplay.getMetrics(metrics)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        newTextBox.x = ((metrics.widthPixels/2)- 150f)
        newTextBox.y = ((metrics.heightPixels/2) - 400f)
        act.addContentView(newTextBox,params)

        return newTextBox
    }

    fun userSendConfirmation(tb: EditText) {
        if (tb.text.isNullOrEmpty()) {
            Toast.makeText(act, "User can't be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (dbLoader.CREATE_NEW_USER(tb.text.toString(), dbLoader.writableDatabase) == -1L) {
            Toast.makeText(act, "User already exists!", Toast.LENGTH_SHORT).show()
            return
        }
        var user: DB_User = dbLoader.GET_SINGLE_USER(dbLoader.writableDatabase, tb.text.toString())

        val intent = Intent(act.baseContext, GameScreen::class.java)
        //Has to be serializable to be sent over!
        intent.putExtra(
            "User",
            user
        )

        Toast.makeText(act,"User created successfully", Toast.LENGTH_SHORT).show()

        act.startActivity(intent)
    }



}
