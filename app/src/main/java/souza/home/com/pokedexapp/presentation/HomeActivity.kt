package souza.home.com.pokedexapp.presentation

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import souza.home.com.extensions.gone
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.home.HomeFragment
import souza.home.com.pokedexapp.presentation.search.SearchDialog
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_POST_1000

class HomeActivity : AppCompatActivity() {

    private lateinit var buttonDiscover : Button
    private lateinit var frameLayoutFragmentHost : FrameLayout
    private lateinit var mainToolbar : Toolbar
    private lateinit var constraintLayoutHome : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        bindViews()
        val splashFragment = SplashScreen()
        val homeFragment = HomeFragment()

        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_home_activity, splashFragment).commit()
        Handler().postDelayed({
            supportFragmentManager.beginTransaction().remove(splashFragment)
            frameLayoutFragmentHost.gone()
            constraintLayoutHome.visible()
        }, DELAY_POST_1000)

        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        buttonDiscover.setOnClickListener {
            constraintLayoutHome.gone()
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_home_activity, homeFragment).commit()
            frameLayoutFragmentHost.visible()
        }
    }

    private fun bindViews(){
        buttonDiscover = findViewById(R.id.button_discover_pokes_home_activity)
        frameLayoutFragmentHost = findViewById(R.id.nav_host_fragment_home_activity)
        mainToolbar = findViewById(R.id.toolbar_home_activity)
        constraintLayoutHome = findViewById(R.id.constraint_layout_home_activity)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search_menu_icon -> {
                openSearchDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun openSearchDialog(){
        val searchDialog = SearchDialog()
        frameLayoutFragmentHost.visible()
        constraintLayoutHome.gone()
        searchDialog.show(supportFragmentManager, getString(R.string.search_fragment_tag))
    }
}