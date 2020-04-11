package souza.home.com.pokedexapp.presentation.details.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import souza.home.com.pokedexapp.presentation.details.details_nested.about.AboutFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain.EvolutionChainFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.others.OthersFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.stats.StatsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.LIMIT_NORMAL_POKES

internal class SectionsPagerAdapter(fm: FragmentManager, val poke: String) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {

    private val TAB_FRAGMENTS_EVOLUTION = arrayOf(
        AboutFragment(poke),
        StatsFragment(poke)
    )

    private val TAB_TITLES_EVOLUTION = arrayOf(
        "About",
        "Base Stats"
    )

    private val TAB_FRAGMENTS = arrayOf(
        AboutFragment(poke),
        StatsFragment(poke),
        EvolutionChainFragment(poke),
        OthersFragment(poke)
    )

    private val TAB_TITLES = arrayOf(
        "About",
        "Base Stats",
        "Evolution",
        "Others"
    )

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        if(Integer.parseInt(poke) > LIMIT_NORMAL_POKES){ // this defines a poke as an evolution! so some fragments will not show.
            return TAB_FRAGMENTS_EVOLUTION[position]
        }
        return TAB_FRAGMENTS[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(Integer.parseInt(poke) > LIMIT_NORMAL_POKES){
            return TAB_TITLES_EVOLUTION[position]
        }
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        if(Integer.parseInt(poke) > LIMIT_NORMAL_POKES){
            return TAB_FRAGMENTS_EVOLUTION.size
        }
        return TAB_FRAGMENTS.size
    }
}