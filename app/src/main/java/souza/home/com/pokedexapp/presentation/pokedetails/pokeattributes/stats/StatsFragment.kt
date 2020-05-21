package souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.stats

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.domain.model.PokeProperty

class StatsFragment(private val pokemonId: Int) : Fragment() {

    private val viewModel by viewModel<StatsViewModel> { parametersOf(pokemonId) }
    private lateinit var hpTextView: TextView
    private lateinit var hpProgressBar: ProgressBar
    private lateinit var attackTextView: TextView
    private lateinit var attackProgressBar: ProgressBar
    private lateinit var defenseTextView: TextView
    private lateinit var defenseProgressBar: ProgressBar
    private lateinit var specialAttackTextView: TextView
    private lateinit var specialAttackProgressBar: ProgressBar
    private lateinit var specialDefenseTextView: TextView
    private lateinit var specialDefenseProgressBar: ProgressBar
    private lateinit var speedTextView: TextView
    private lateinit var speedProgressBar: ProgressBar
    private lateinit var heightTextView: TextView
    private lateinit var weightTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poke_stats, container, false)
        bindViews(view)
        initObservers()

        return view
    }

    private fun bindViews(view: View) {
        hpProgressBar = view.findViewById(R.id.progress_bar_hp_stat)
        attackProgressBar = view.findViewById(R.id.progress_bar_attack_stat)
        defenseProgressBar = view.findViewById(R.id.progress_bar_defense_stat)
        specialAttackProgressBar = view.findViewById(R.id.progress_bar_special_attack_stat)
        specialDefenseProgressBar = view.findViewById(R.id.progress_bar_special_defense_stat)
        speedProgressBar = view.findViewById(R.id.progress_bar_speed_stat)

        attackTextView = view.findViewById(R.id.text_view_poke_attack_stat)
        hpTextView = view.findViewById(R.id.text_view_poke_hp_stat)
        defenseTextView = view.findViewById(R.id.text_view_poke_defense_stat)
        specialAttackTextView = view.findViewById(R.id.text_view_poke_special_attack_stat)
        specialDefenseTextView = view.findViewById(R.id.text_view_poke_special_defense_stat)
        speedTextView = view.findViewById(R.id.text_view_poke_speed_stat)
        weightTextView = view.findViewById(R.id.text_view_detail_weight_stat)
        heightTextView = view.findViewById(R.id.text_view_detail_height_stat)
    }

    private fun initObservers() {
        viewModel.apply {
            this.updatePropertiesOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    initStats(it)
                }
            })
        }
    }

    private fun initStats(item: PokeProperty?) {

        item?.stats?.get(5)?.baseStat.let { it?.let { it1 -> Integer.valueOf(it1) } }?.let { animateStats(it, hpTextView) }
        hpProgressBar.progress = item?.stats?.get(5)?.baseStat?.let { Integer.parseInt(it) }!!

        item.stats[4].baseStat?.let { Integer.valueOf(it) }?.let { animateStats(it, attackTextView) }
        attackProgressBar.progress = item.stats[4].baseStat?.let { Integer.parseInt(it) }!!

        item.stats[3].baseStat?.let { Integer.valueOf(it) }?.let { animateStats(it, defenseTextView) }
        defenseProgressBar.progress = item.stats[3].baseStat?.let { Integer.parseInt(it) }!!

        item.stats[2].baseStat?.let { Integer.valueOf(it) }?.let { animateStats(it, specialAttackTextView) }
        specialAttackProgressBar.progress = item.stats[2].baseStat?.let { Integer.parseInt(it) }!!

        item.stats[1].baseStat?.let { Integer.valueOf(it) }?.let { animateStats(it, specialDefenseTextView) }
        specialDefenseProgressBar.progress = item.stats[1].baseStat?.let { Integer.parseInt(it) }!!

        item.stats[0].baseStat?.let { Integer.valueOf(it) }?.let { animateStats(it, speedTextView) }
        speedProgressBar.progress = item.stats[0].baseStat?.let { Integer.parseInt(it) }!!

        animateStats(item.weight?.let { Integer.valueOf(it) }, weightTextView)
        animateStats(item.height?.let { Integer.valueOf(it) }, heightTextView)
    }

    private fun animateStats(item: Int?, tv: TextView) {
        val statsAnimator = ValueAnimator()
        statsAnimator.setObjectValues(0, item)
        statsAnimator.addUpdateListener {
                animation -> tv.text = animation.animatedValue.toString()
        }
        statsAnimator.duration = 600
        statsAnimator.start()
    }
}
