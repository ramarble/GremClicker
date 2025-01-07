package com.example.gremclicker.Currency

class Currency {

    companion object {
        var currency: Int = 0
        fun addCurrency(currency: Int) {
            this.currency += currency
        }
    }

}