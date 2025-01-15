package com.example.gremclicker.ViewActivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.DisplayMetrics
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


class LoginScreen : AppCompatActivity() {

    val dbLoader = Database(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        dbLoader.CREATE_DATABASE_IF_NOT_EXISTS(dbLoader.writableDatabase)

        val text: TextView = findViewById(R.id.gremClickerTitle)
        text.setTextSize(30f)

        //DEBUG TO TEST THAT THE BOOLEAN ARRAY WORKS. UNIMPLEMENTED.
        var v: BooleanArray = MathClass.returnUpgradesAsBooleanArray(61)

        val newUserButton = findViewById<Button>(R.id.newUserButton)
        newUserButton.setOnClickListener {
            newUser(this,newUserButton)
        }

        val LoadPlayerButton = findViewById<Button>(R.id.loadplayerbutton)
        LoadPlayerButton.setOnClickListener {

            val intent = Intent(baseContext, LoadUserScreen::class.java)
            startActivity(intent)

        }

    }

    fun newUser(act: Activity, b: Button) {
        b.text = "Start Game"
        //delete the clicklistener asap to avoid issues with race conditions
        b.setOnClickListener( null)
        val newTextBox = createTextBox()
        //I just took this code from somewhere else
        newTextBox.requestFocus()
        val inputMethodManager: InputMethodManager = act.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(newTextBox, 0)

        b.setOnClickListener {
            userSendConfirmation(newTextBox)
        }

    }
    fun createTextBox() : EditText {
        val newTextBox = EditText(this)
        newTextBox.inputType = InputType.TYPE_CLASS_TEXT
        newTextBox.minimumWidth = 300
        newTextBox.width = 300

        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        val params = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        newTextBox.x = ((metrics.widthPixels/2)- 150f)
        newTextBox.y = ((metrics.heightPixels/2) - 400f)
        this.addContentView(newTextBox,params)

        return newTextBox
    }

    fun userSendConfirmation(tb: EditText) {
        if (tb.text.isNullOrEmpty()) {
            Toast.makeText(this, "User can't be empty!", Toast.LENGTH_SHORT).show()
            return
        }

        if (dbLoader.CREATE_NEW_USER(tb.text.toString(), dbLoader.writableDatabase) == -1L) {
            Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show()
            return
        }
        val user: DB_User = dbLoader.GET_SINGLE_USER(dbLoader.writableDatabase, tb.text.toString())

        val intent = Intent(this.baseContext, GameScreen::class.java)
        //Has to be serializable to be sent over!
        intent.putExtra(
            "User",
            user
        )

        Toast.makeText(this,"User created successfully", Toast.LENGTH_SHORT).show()
        startActivity(intent)
    }



}
