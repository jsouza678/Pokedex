package com.souza.pokedetail.presentation.pokedetails

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.souza.pokedetail.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val pokeId = intent.getIntExtra("pokeId", 0)
        val pokeName = intent.getStringExtra("pokeName")
        val detailsFragment = DetailsFragment(pokemonId = pokeId, pokemonName = pokeName)

        changeFragment(detailsFragment)
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment_details_activity, fragment)
            .commit()
    }
}
