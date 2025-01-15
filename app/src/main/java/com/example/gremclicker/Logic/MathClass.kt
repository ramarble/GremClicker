package com.example.gremclicker.Logic

import kotlin.math.sqrt

class MathClass {
     companion object {
         fun returnUpgradesAsBooleanArray(intUpgrades: Int): BooleanArray {
             var size = sqrt(intUpgrades.toDouble()).toInt()
             var barr = BooleanArray(size)
             var u = intUpgrades
             for (i in 0 until size) {
                 barr[i] = intToBool(u)
                 u /= 2
             }
             return barr
         }

         fun intToBool(i: Int) : Boolean {
             return ((i%2) == 1)
         }
     }
}