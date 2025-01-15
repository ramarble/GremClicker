package com.example.gremclicker.ViewActivities
import android.app.Activity
import android.database.SQLException
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.gremclicker.Logic.customOnItemClickListener
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database

class LoadUserScreen() : AppCompatActivity(){

    var dbLoader = Database(this);

    override fun onResume() {
        loadPlayerList(this, dbLoader)
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.load_player)
        loadPlayerList(this, dbLoader)

        findViewById<Button>(R.id.buttonGoBack).setOnClickListener({
            LoginScreen(this).main()
        })

        findViewById<Button>(R.id.buttonClearDatabase).setOnClickListener({
            dbLoader.CLEAR_DATABASE(dbLoader.writableDatabase)
            dbLoader.RECREATE_DATABASE(dbLoader.writableDatabase)
            LoginScreen(this).main()
        })
    }


    fun loadPlayerList(a: Activity, db: Database) {
        var tb = a.findViewById<ListView>(R.id.selectUserLayout)

        var list: Array<DB_User> = returnUserArray(db)
        var v: ArrayAdapter<DB_User>
        if (!list.isEmpty()) {
            tb.onItemClickListener = customOnItemClickListener(a, db, list)
        } else {
            var err = DB_User("No user found!", -1, -1)
            list = Array(1) {err}
        }
        v = ArrayAdapter(a.baseContext, android.R.layout.simple_list_item_1, list)
        tb.setAdapter(v)
    }

    fun returnUserArray(dbLoader: Database): Array<DB_User> {
        var list: ArrayList<DB_User> = ArrayList()
        try {
            var c = dbLoader.GET_ALL_USERS(dbLoader.writableDatabase)
            if (c.moveToFirst()) {
                do {
                    list.add(DB_User(c.getString(0), c.getInt(1), 3))
                } while (c.moveToNext());
            }
            c.close()
            return list.toTypedArray()
        } catch (e: SQLException) {
            return list.toTypedArray()
        }
    }
}