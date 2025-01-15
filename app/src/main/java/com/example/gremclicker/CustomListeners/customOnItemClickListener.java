package com.example.gremclicker.CustomListeners;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.gremclicker.SQLite.DB_User;
import com.example.gremclicker.SQLite.Database;
import com.example.gremclicker.ViewActivities.GameScreen;

public class customOnItemClickListener implements AdapterView.OnItemClickListener {
    static Activity activity;
    static Database database;
    static DB_User[] arrayUsers;
    public customOnItemClickListener(Activity ac, Database db, DB_User[] arr) {
        activity = ac;
        database = db;
        arrayUsers = arr;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(activity.getBaseContext(), GameScreen.class);

        //Has to be serializable to be sent over!
        intent.putExtra("User", arrayUsers[position]);
        activity.startActivity(intent);
    }
}
