package souza.home.com.pokedexapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import souza.home.com.pokedexapp.ui.details.DetailsPokedexFragment
import souza.home.com.pokedexapp.ui.home.HomePokedexFragment

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

   /*     val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        val myToolbar = findViewById<Toolbar>(R.id.toolbar)


        setSupportActionBar(myToolbar)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            //setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true
            drawerLayout.closeDrawers()

            true
        }*/

        val textFragment = HomePokedexFragment()
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment, textFragment).commit()
    }

/*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            android.R.id.home ->{
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }
*/

}
