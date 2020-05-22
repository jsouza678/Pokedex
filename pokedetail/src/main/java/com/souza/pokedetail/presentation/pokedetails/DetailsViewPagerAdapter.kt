package com.souza.pokedetail.presentation.pokedetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.about.AboutFragment
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain.EvolutionChainFragment
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.OthersFragment
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.stats.StatsFragment
import com.souza.pokedetail.utils.Constants.Companion.LIMIT_NORMAL_POKES

internal class DetailsViewPagerAdapter(
    fm: FragmentManager,
    val pokemonId: Int,
    pokeChainId: Int
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val evolutionPokeFragments = arrayOf(
        AboutFragment(pokemonId),
        StatsFragment(pokemonId)
    )

    private val evolutionPokeTitles = arrayOf(
        "About",
        "Base Stats"
    )

    private val defaultPokeFragments = arrayOf(
        AboutFragment(pokemonId),
        StatsFragment(pokemonId),
        EvolutionChainFragment(pokeChainId),
        OthersFragment(pokemonId)
    )

    private val defaultPokeTitles = arrayOf(
        "About",
        "Base Stats",
        "Evolution",
        "Others"
    )

    override fun getItem(position: Int): Fragment {
        if (pokemonId > LIMIT_NORMAL_POKES) { // this defines a poke as an evolution! so some fragments will not show.
            return evolutionPokeFragments[position]
        }
        return defaultPokeFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (pokemonId > LIMIT_NORMAL_POKES) {
            return evolutionPokeTitles[position]
        }
        return defaultPokeTitles[position]
    }

    override fun getCount(): Int {
        if (pokemonId > LIMIT_NORMAL_POKES) {
            return evolutionPokeFragments.size
        }
        return defaultPokeFragments.size
    }
}
