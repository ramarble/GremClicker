package com.example.gremclicker.Loader

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.example.gremclicker.Currency.Currency.Companion.currency
import com.example.gremclicker.R
import com.example.gremclicker.SQLite.DB_User
import com.example.gremclicker.SQLite.Database


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility", "SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContentView(R.layout.login)
        val DatabaseClass = Database(baseContext)
        val db = DatabaseClass.writableDatabase
        val b = findViewById<Button>(R.id.button)
        b.setOnClickListener {
            DatabaseClass.newUser("hi", db)
        }

        val help = findViewById<Button>(R.id.loadplayerbutton)
        help.setOnClickListener {
            setContentView(R.layout.load_player)
            loadPlayerList(db)
        }

        /*
        setContentView(R.layout.grem_button_main)
        val animatedGrem = findViewById<ImageView?>(R.id.animatedGrem)
        val gremButton = GremButton(animatedGrem);
        fakeFrameUpdate()
        //createOtomo()
        */
    }

    fun loadPlayerList(db: SQLiteDatabase) {
        var tb = findViewById<TableLayout>(R.id.selectUserLayout)
        var list: ArrayList<DB_User> = findUser(db)
        addRowToTable(tb, "ID", "NOMBRE", "PUNTOS")
        for (d in list) {
            addRowToTable(tb, d.id.toString(), d.name, d.grems.toString())
        }
    }

    fun addRowToTable(tb: TableLayout, id: String, name: String, grems: String) {
        var row = TableRow(this)
        var textId = TextView(this)
        var textName = TextView(this)
        var textGrems = TextView(this)
        textId.gravity = Gravity.LEFT
        textName.gravity = Gravity.CENTER
        textGrems.gravity = Gravity.RIGHT
        textId.text = id

        row.setPaddingRelative(0,10,0,10)
        textId.setTextSize(TypedValue.COMPLEX_UNIT_SP,18F)
        textName.setTextSize(TypedValue.COMPLEX_UNIT_SP,18F)
        textName.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
        textGrems.setTextSize(TypedValue.COMPLEX_UNIT_SP,18F)
        textName.text = name
        textGrems.text = grems
        row.addView(textId)
        row.addView(textName)
        row.addView(textGrems)

        var rowBlack = TableRow(this)
        rowBlack.setBackgroundColor(Color.BLACK)
        rowBlack.setPadding(0,0,0,3)

        row.setOnClickListener {
            Toast.makeText(this, (row.children.first() as TextView).text, Toast.LENGTH_SHORT).show()
        }
        tb.addView(row, TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
        tb.addView(rowBlack, TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))

    }

    fun findUser(db: SQLiteDatabase): ArrayList<DB_User> {
        var c: Cursor = db.rawQuery("SELECT * from ${Database.TABLE_NAME}", null)
        var list: ArrayList<DB_User> = ArrayList()
        if (c.moveToFirst()) {
            do {
                list.add(DB_User(c.getInt(0), c.getString(1), c.getInt(2)))
            } while (c.moveToNext());
        }
        c.close()

        return list
    }

    fun fakeFrameUpdate() {
        Thread(Runnable {
            while (true) {
                runOnUiThread({
                    findViewById<TextView>(R.id.currencyDisplay).text = currency.toString()
                })
                Thread.sleep(20)
            }
        }).start()
    }

    @SuppressLint("ResourceType", "Recycle")
    fun createOtomo() {

        var iv = ImageView(this)
        var lp = LayoutParams(200, LayoutParams.MATCH_PARENT)
        iv.setImageResource(R.drawable.otomo2);

        this.addContentView(iv, lp);
        var animation = ObjectAnimator.ofFloat(iv, "translationY", 13000f).setDuration(10000)
        var rot = ObjectAnimator.ofFloat(iv, "rotation", 0f, 360f)
        rot.repeatCount = 1000;
        rot.start()
        animation.start()
        iv.setOnClickListener { animation.cancel(); rot.cancel() }


        }
    }