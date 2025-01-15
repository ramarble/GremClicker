package com.example.gremclicker.SQLite;

import java.io.Serializable
import kotlin.math.sqrt

data class DB_User(
    var name: String,
    var grems: Int,
    var upgrades: Int,
    var grems_per_second: Int
    ) : Serializable {
    override fun toString(): String {
        return name + ": " + grems + " grems " + "| grems per second: " + grems_per_second
    }

}

