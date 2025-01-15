package com.example.gremclicker.SQLite;

import android.R.id
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
                        "UPGRADE_BITMASK INTEGER," +
                        "GREMS_PER_SECOND INTEGER)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

        fun CREATE_NEW_USER(name: String, db: SQLiteDatabase) : Long{
                db.execSQL(SQL_CREATE_ENTRIES)
                var cv = ContentValues()
                cv.put("NAME", name)
                cv.put("GREMS", 0)
                cv.put("GREMS_PER_SECOND", 0)
                cv.put("UPGRADE_BITMASK", 0)
                return db.insert("$TABLE_NAME", null, cv);
        }

        fun UPDATE_USER(name: String, Grems: Int, gps: Int, db:SQLiteDatabase) {

                var cv = ContentValues()
                cv.put("NAME", name)
                cv.put("GREMS", Grems)
                cv.put("GREMS_PER_SECOND", gps)
                var s = "NAME = ?"
                val whereArgs = arrayOf<String>(name)
                db.update("$TABLE_NAME", cv, s, whereArgs);
        }

        fun CLEAR_DATABASE(db:SQLiteDatabase) {
                db.execSQL(SQL_DELETE_ENTRIES)
        }

        fun CREATE_DATABASE_IF_NOT_EXISTS(db:SQLiteDatabase) {
                db.execSQL(SQL_CREATE_ENTRIES)
        }

        fun GET_SINGLE_USER(db:SQLiteDatabase, str: String) : DB_User{
                var c = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE NAME = \"$str\"", null)
                c.moveToFirst()
                var userReturned = DB_User(
                        c.getString(0),
                        c.getInt(1),
                        c.getInt(2),
                        c.getInt(3))
                c.close()
                return userReturned
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


