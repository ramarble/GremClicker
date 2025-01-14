package com.example.gremclicker.SQLite;

import java.io.Serializable

data class DB_User(var id: Int, var name: String,var grems: Int) : Serializable {
    override fun toString(): String {
        return name + ": " + grems + " grems"
    }
}

