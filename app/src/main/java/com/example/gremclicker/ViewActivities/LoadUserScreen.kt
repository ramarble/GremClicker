package com.example.gremclicker.ViewActivities
import android.app.Activity
import android.database.SQLException
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database

class LoadUserScreen(a: Activity, dbLoader: Database){

    var a: Activity = a;
    var dbLoader: Database = dbLoader;
    fun main() {
        a.setContentView(R.layout.load_player)
        loadPlayerList(a, dbLoader)

        a.findViewById<Button>(R.id.buttonGoBack).setOnClickListener({
            LoginScreen(a).main()
        })

        a.findViewById<Button>(R.id.buttonClearDatabase).setOnClickListener({
            dbLoader.CLEAR_DATABASE(dbLoader.writableDatabase)
            LoginScreen(a).main()
        })
    }


    fun loadPlayerList(a: Activity, db: Database) {
        var tb = a.findViewById<ListView>(R.id.selectUserLayout)

        var list: Array<DB_User> = returnUserArray(db)
        var v: ArrayAdapter<DB_User>
        if (!list.isEmpty()) {
            tb.onItemClickListener = com.example.gremclicker.ButtonLogic.customOnItemClickListener(a, db)
        } else {
            var err = DB_User(-1, "No user found!", -1)
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
                    list.add(DB_User(c.getInt(0), c.getString(1), c.getInt(2)))
                } while (c.moveToNext());
            }
            c.close()
            return list.toTypedArray()
        } catch (e: SQLException) {
            return list.toTypedArray()
        }
    }
}