package com.example.gremclicker.ViewActivities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gremclicker.Currency.Currency
import com.example.gremclicker.Currency.Currency.Companion.buyUpgradeOne
import com.example.gremclicker.Currency.Currency.Companion.buyUpgradeTwo
import com.example.gremclicker.Currency.Currency.Companion.gremsPerSecond
import com.example.gremclicker.Logic.*
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database
import com.example.gremclicker.Threads.RandomOtomoRainThread
import com.example.gremclicker.Threads.TextUpdateThread
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import customDrawerListener


class GameScreen: AppCompatActivity() {


    lateinit var User: DB_User
    lateinit var otomoRain: RandomOtomoRainThread

    override fun onStop() {
        //Saved myself from a massive memory leak
        otomoRain.runOtomoThread = false
        super.onStop()
    }

    override fun onResume() {
        User = getIntent().getSerializableExtra("User") as DB_User
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.grem_button_main)
        User = getIntent().getSerializableExtra("User") as DB_User

        val dbLoader = Database(baseContext)

        Currency.setCurrency(User.grems)
        gremsPerSecond = User.grems_per_second


        val CURRENT_POINTS: TextView = findViewById(R.id.currencyDisplay)

        Thread({Currency().run()}).start()
        Thread({ TextUpdateThread(this).run()}).start()
        otomoRain = RandomOtomoRainThread(this, 2000)
        Thread({otomoRain.run()}).start()

        createSideBar()


        val animatedGrem = findViewById<ImageView?>(R.id.animatedGrem)
        GremButton(animatedGrem)

        val saveButton: TextView = this.findViewById(R.id.buttonSaveData)
        saveButton.setTextSize(10f)

        saveButton.setOnClickListener{
            dbLoader.UPDATE_USER(
                User.name,
                Integer.parseInt(CURRENT_POINTS.text.toString()),
                gremsPerSecond,
                dbLoader.writableDatabase)
        }

    }

    fun createSideBar() {
        //https://danielme.com/2018/12/19/diseno-android-menu-lateral-con-navigation-drawer/
        //Absolute god
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(findViewById(R.id.toolbar))
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        supportActionBar?.title = "Upgrades"

        //Cooked this myself
        val cabt = customDrawerListener(this)
        drawerLayout.addDrawerListener(cabt)

        val navView = findViewById<NavigationView>(R.id.navigation_view)
        navView.setNavigationItemSelectedListener{
            customNav(User).onNavigationItemSelected(it)
        }

        toggle.syncState()
    }


    class customNav(var user: DB_User) : OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {

            when(item.title) {
                "Upgrade 1" -> if(buyUpgradeOne(user)) {
                    item.isEnabled = false
                    item.isCheckable = false
                }
                "Upgrade 2" -> if(buyUpgradeTwo(user)) {
                    item.isEnabled = false
                }
                else -> Log.e("I tried", "I tried")
            }
            return true
        }

    }

}

