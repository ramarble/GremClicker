import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.gremclicker.R

class CustomDrawerListener(act: Activity) : DrawerLayout.DrawerListener {

    var drawer: DrawerLayout = act.findViewById(R.id.drawer_layout)
    var grem = act.findViewById<ImageView>(R.id.animatedGrem)
    var button = act.findViewById<Button>(R.id.buttonSaveData)
    override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
        if (slideOffset > 0.03F) {
            drawer.bringToFront()
        } else {
            grem.bringToFront()
            button.bringToFront()
        }
    }

    override fun onDrawerOpened(drawerView: View) {
        drawer.bringToFront()
    }

    override fun onDrawerClosed(drawerView: View) {
        grem.bringToFront()
        button.bringToFront()
    }

    override fun onDrawerStateChanged(newState: Int) {
        return
    }

}