package com.example.gremclicker.ButtonLogic;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.gremclicker.SQLite.Database;
import com.example.gremclicker.ViewActivities.GameScreen;
import com.example.gremclicker.ViewActivities.MainActivity;

public class customOnItemClickListener implements AdapterView.OnItemClickListener {
    static Activity activity;
    static Database database;
    public customOnItemClickListener(Activity ac, Database db) {
        activity = ac;
        database = db;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("sdfg", "position : " + position + "\n id: " + id);
        Intent intent = new Intent(activity.getBaseContext(), GameScreen.class);

        //Has to be serializable to be sent over!
        intent.putExtra("User", database.GET_SINGLE_USER(database.getWritableDatabase(), (int) id));
        activity.startActivity(intent);
    }
}
