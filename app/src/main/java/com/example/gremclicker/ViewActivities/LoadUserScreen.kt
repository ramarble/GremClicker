package com.example.gremclicker.ViewActivities
import android.content.Intent
import android.database.SQLException
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.CustomListeners.customOnItemClickListener
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database

class LoadUserScreen() : AppCompatActivity(){

    var dbLoader = Database(this);

    override fun onResume() {
        loadPlayerList(dbLoader)
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_player)
        loadPlayerList(dbLoader)

        findViewById<Button>(R.id.buttonGoBack).setOnClickListener({
            startActivity(Intent(this, LoginScreen::class.java))

        })

        findViewById<Button>(R.id.buttonClearDatabase).setOnClickListener({
            dbLoader.CLEAR_DATABASE(dbLoader.writableDatabase)
            dbLoader.CREATE_DATABASE_IF_NOT_EXISTS(dbLoader.writableDatabase)
            startActivity(Intent(this, LoginScreen::class.java))
        })
    }


    fun loadPlayerList(db: Database) {
        var listOfUsersLayout = findViewById<ListView>(R.id.selectUserLayout)

        var list: Array<DB_User> = returnUserArray(db)
        var arrayAdapterForList: ArrayAdapter<DB_User>
        if (!list.isEmpty()) {
            listOfUsersLayout.onItemClickListener =
                customOnItemClickListener(
                    this,
                    db,
                    list
                )
        } else {
            var err = DB_User("No user found!", -1, -1, -1)
            list = Array(1) {err}
        }
        arrayAdapterForList = ArrayAdapter(this.baseContext, android.R.layout.simple_list_item_1, list)
        listOfUsersLayout.setAdapter(arrayAdapterForList)
    }

    fun returnUserArray(dbLoader: Database): Array<DB_User> {
        var list: ArrayList<DB_User> = ArrayList()
        try {
            var cursor = dbLoader.GET_ALL_USERS(dbLoader.writableDatabase)
            if (cursor.moveToFirst()) {
                do {
                    list.add(
                        DB_User(
                            cursor.getString(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3)))
                } while (cursor.moveToNext());
            }
            cursor.close()
            return list.toTypedArray()
        } catch (e: SQLException) {
            return list.toTypedArray()
        }
    }
}