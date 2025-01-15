package com.example.gremclicker.SQLite;

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
                const val DATABASE_NAME = "Users.db"
                const val TABLE_NAME = "Users"
                const val DATABASE_VERSION = 1
        }

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS $TABLE_NAME ("+
                        "NAME TEXT PRIMARY KEY NOT NULL," +
                        "GREMS INTEGER," +
                        "UPGRADE_BITMASK INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

        fun CREATE_NEW_USER(name: String, db: SQLiteDatabase) : Long{
                db.execSQL(SQL_CREATE_ENTRIES)
                var cv = ContentValues()
                cv.put("NAME", name)
                cv.put("GREMS", 0)
                return db.insert("$TABLE_NAME", null, cv);
        }

        fun UPDATE_USER(name: String, Grems: Int, db:SQLiteDatabase) {
                db.execSQL("UPDATE $TABLE_NAME SET GREMS=$Grems WHERE NAME = $name")
        }

        fun CLEAR_DATABASE(db:SQLiteDatabase) {
                db.execSQL(SQL_DELETE_ENTRIES)
        }

        fun RECREATE_DATABASE(db:SQLiteDatabase) {
                db.execSQL(SQL_CREATE_ENTRIES)
        }

        fun GET_SINGLE_USER(db:SQLiteDatabase, str: String) : DB_User{
                var c = db.rawQuery("SELECT * FROM ${Database.TABLE_NAME} WHERE NAME = \"$str\"", null)
                c.moveToFirst()
                c.close()
                return DB_User(c.getString(0), c.getInt(1),3)
        }

        fun GET_ALL_USERS(db:SQLiteDatabase) : Cursor{
                return db.rawQuery("SELECT * from ${Database.TABLE_NAME}", null)
        }

        override fun onCreate(db: SQLiteDatabase) {
                db.execSQL(SQL_CREATE_ENTRIES)
        }
        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
                throw NotImplementedError()
        }
}


