package souza.home.com.pokedexapp.presentation.details

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import souza.home.com.pokedexapp.presentation.details.details_nested.about.AboutFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain.EvolutionChainFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.others.OthersFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.stats.StatsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.LIMIT_NORMAL_POKES

internal class DetailsViewPagerAdapter(fm: FragmentManager, val pokeId: Int, val pokeChainId: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_FRAGMENTS_EVOLUTION = arrayOf(
        AboutFragment(pokeId),
        StatsFragment(pokeId)
    )

    private val TAB_TITLES_EVOLUTION = arrayOf(
        "About",
        "Base Stats"
    )

    private val TAB_FRAGMENTS = arrayOf(
        AboutFragment(pokeId),
        StatsFragment(pokeId),
        EvolutionChainFragment(pokeChainId),
        OthersFragment(pokeId)
    )

    private val TAB_TITLES = arrayOf(
        "About",
        "Base Stats",
        "Evolution",
        "Others"
    )

    override fun getItem(position: Int): Fragment {
        if (pokeId > LIMIT_NORMAL_POKES) { // this defines a poke as an evolution! so some fragments will not show.
            return TAB_FRAGMENTS_EVOLUTION[position]
        }
        return TAB_FRAGMENTS[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if (pokeId > LIMIT_NORMAL_POKES) {
            return TAB_TITLES_EVOLUTION[position]
        }
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        if (pokeId > LIMIT_NORMAL_POKES) {
            return TAB_FRAGMENTS_EVOLUTION.size
        }
        return TAB_FRAGMENTS.size
    }
}
