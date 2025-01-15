package com.example.gremclicker.Currency

import com.example.gremclicker.SQLite.DB_User
import java.lang.Thread.sleep

class Currency : Runnable{

    companion object {
        private var currency: Int = 0

        @Volatile
        var gremsPerSecond: Int = 0;

        @Volatile
        var runCurrencyThread = true

        fun addCurrency(currency: Int) {
            this.currency += currency
        }

        fun setCurrency(currency: Int) {
            this.currency = currency
        }

        fun getCurrency(): Int {
            return this.currency
        }


        fun buyUpgradeOne(user: DB_User) : Boolean {
            if (currency >= 100) {
                currency -= 100
                user.upgrades += 1
                gremsPerSecond += 10
                return true;
            }
            return false
        }

        fun buyUpgradeTwo(user: DB_User) : Boolean {
            if (currency >= 250) {
                currency -= 250
                user.upgrades += 2
                gremsPerSecond += 30
                return true;
            }
            return false
        }
    }

    override fun run() {
        while (runCurrencyThread) {
            sleep(1000)
            addCurrency(gremsPerSecond)
        }
    }


}