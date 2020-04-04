package souza.home.com.pokedexapp.ui.details.viewpager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import souza.home.com.pokedexapp.ui.details.about.PokeAboutFragment
import souza.home.com.pokedexapp.ui.details.chain.PokeChainFragment
import souza.home.com.pokedexapp.ui.details.other.PokeOthersFragment
import souza.home.com.pokedexapp.ui.details.stats.PokeStatsFragment

internal class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val poke: String) :
    FragmentPagerAdapter(fm) {

    private val TAB_FRAGMENTS = arrayOf(
        PokeAboutFragment(poke),
        PokeStatsFragment(poke),
        PokeChainFragment(poke),
        PokeOthersFragment(poke)
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
        return TAB_FRAGMENTS[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return TAB_TITLES[position]
    }

    override fun getCount(): Int {
        return TAB_FRAGMENTS.size
    }
}