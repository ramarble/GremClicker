package com.example.gremclicker.SQLite;

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
                const val DATABASE_NAME = "Users.db"
                const val TABLE_NAME = "Users"
                const val DATABASE_VERSION = 1
        }

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        "NAME TEXT," +
                        "GREMS INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"



        fun newUser(name: String, db: SQLiteDatabase) {
                var cv = ContentValues()
                cv.put("NAME", name)
                cv.put("GREMS", 0)
                db.insert("$TABLE_NAME", null, cv);
        }

        override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(SQL_CREATE_ENTRIES)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                throw NotImplementedError()
        }
}


