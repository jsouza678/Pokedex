package com.souza.pokedetail.presentation.pokedetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.souza.connectivity.Connectivity
import com.souza.pokedetail.R
import com.souza.pokedetail.utils.Constants.Companion.ABSOLUTE_ZERO
import org.koin.android.viewmodel.ext.android.viewModel

class PokeDetailsActivity : AppCompatActivity() {

    private lateinit var connectivity: Connectivity
    private lateinit var connectivitySnackbar: Snackbar
    private val viewModel by viewModel<PokeDetailsActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val pokeId = intent.getIntExtra(getString(R.string.poke_id_extra_activity_home_to_details), ABSOLUTE_ZERO)
        val pokeName: String? = intent.getStringExtra(getString(R.string.poke_name_extra_activity_home_to_details))
        val detailsFragment = pokeName?.let { PokeDetailsFragment(pokemonId = pokeId, pokemonName = it) }

        detailsFragment?.let { changeFragment(fragment = it) }

        initConnectivityCallback()
        initConnectivitySnackbar()
        initConnectivityObserver()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_details_activity, fragment)
            .commit()
    }

    private fun initConnectivityObserver() {
        connectivity.observe(this@PokeDetailsActivity, Observer { connectivityResult ->
            viewModel.mustShowConnectivitySnackbar(hasNetworkConnectivity = connectivityResult)
        })

        viewModel.apply {
            showConnectivityOnSnackbar().observe(this@PokeDetailsActivity, Observer {
                this@PokeDetailsActivity.showConnectivityOnSnackbar()
            })

            showConnectivityOffSnackbar().observe(this@PokeDetailsActivity, Observer {
                this@PokeDetailsActivity.showConnectivityOffSnackbar()
            })
        }
    }

    private fun initConnectivityCallback() {
        connectivity = Connectivity(application)
    }

    private fun initConnectivitySnackbar() {
        connectivitySnackbar =
            Snackbar.make(
                findViewById(R.id.nav_host_fragment_details_activity),
                getString(R.string.snackbar_message_internet_back),
                Snackbar.LENGTH_INDEFINITE
            )
    }

    private fun showConnectivityOnSnackbar() {
        connectivitySnackbar.duration = Snackbar.LENGTH_SHORT
        connectivitySnackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.poke_green))
        connectivitySnackbar.setText(getString(R.string.snackbar_message_internet_back))
        connectivitySnackbar.show()
    }

    private fun showConnectivityOffSnackbar() {
        connectivitySnackbar.duration = Snackbar.LENGTH_INDEFINITE
        connectivitySnackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.poke_red))
        connectivitySnackbar.setText(getString(R.string.snackbar_internet_off))
        connectivitySnackbar.show()
    }
}
