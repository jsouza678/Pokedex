package souza.home.com.pokedexapp.presentation.home

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel
import souza.home.com.connectivity.Connectivity
import souza.home.com.extensions.gone
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.pokecatalog.PokeCatalogFragment
import souza.home.com.pokedexapp.presentation.search.SearchDialog
import souza.home.com.pokedexapp.utils.Constants.Companion.DELAY_POST_1000

class HomeActivity : AppCompatActivity() {

    private lateinit var buttonDiscover: Button
    private lateinit var frameLayoutFragmentHost: FrameLayout
    private lateinit var mainToolbar: Toolbar
    private lateinit var snackbar: Snackbar
    private var hasNetworkConnectivity = true
    private lateinit var connectivity: Connectivity
    private lateinit var constraintLayoutHome: ConstraintLayout
    private val viewModel by viewModel<HomeViewModel>()
    private val splashFragment =
        SplashScreen()
    private val homeFragment = PokeCatalogFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bindViews()
        initSplashScreen()
        setToolbar()
        initButtonDiscoverPokes()
        initConnectivityCallback()
        initConnectivitySnackbar()
        initConnectivityObserver()
    }

    private fun bindViews() {
        buttonDiscover = findViewById(R.id.button_discover_pokes_home_activity)
        frameLayoutFragmentHost = findViewById(R.id.nav_host_fragment_home_activity)
        mainToolbar = findViewById(R.id.toolbar_home_activity)
        constraintLayoutHome = findViewById(R.id.constraint_layout_home_activity)
    }

    private fun initSplashScreen() {
        openSplashFragment()
        Handler().postDelayed({
            closeSplashFragment()
        }, DELAY_POST_1000)
    }

    private fun openSplashFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_home_activity, splashFragment).commit()
    }

    private fun closeSplashFragment() {
        supportFragmentManager.beginTransaction().remove(splashFragment)
        frameLayoutFragmentHost.gone()
        constraintLayoutHome.visible()
    }

    private fun setToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_menu_icon -> {
                openSearchDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initButtonDiscoverPokes() {
        buttonDiscover.setOnClickListener {
            constraintLayoutHome.gone()
            supportFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_home_activity, homeFragment).commit()
            frameLayoutFragmentHost.visible()
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(this@HomeActivity, Observer { hasNetworkConnectivity ->
            this.hasNetworkConnectivity = hasNetworkConnectivity
            viewModel.mustShowConnectivitySnackbar(hasNetworkConnectivity)
        })

        viewModel.apply {
            showConnectivityOnSnackbar().observe(this@HomeActivity, Observer {
                this@HomeActivity.showConnectivityOnSnackbar()
            })

            showConnectivityOffSnackbar().observe(this@HomeActivity, Observer {
                this@HomeActivity.showConnectivityOffSnackbar()
            })
        }
    }

    private fun initConnectivityCallback() {
        connectivity = Connectivity(application)
    }

    private fun initConnectivitySnackbar() {
        snackbar =
            Snackbar.make(
                findViewById(R.id.nav_host_fragment_home_activity),
                getString(R.string.snackbar_message_internet_back),
                Snackbar.LENGTH_INDEFINITE
            )
    }

    private fun showConnectivityOnSnackbar() {
        snackbar.duration = Snackbar.LENGTH_SHORT
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.poke_green))
        snackbar.setText(getString(R.string.snackbar_message_internet_back))
        snackbar.show()
    }

    private fun showConnectivityOffSnackbar() {
        snackbar.duration = Snackbar.LENGTH_INDEFINITE
        snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.poke_red))
        snackbar.setText(getString(R.string.snackbar_internet_off))
        snackbar.show()
    }

    private fun openSearchDialog() {
        val searchDialog = SearchDialog()
        frameLayoutFragmentHost.visible()
        constraintLayoutHome.gone()
        searchDialog.show(supportFragmentManager, getString(R.string.search_fragment_tag))
    }
}
