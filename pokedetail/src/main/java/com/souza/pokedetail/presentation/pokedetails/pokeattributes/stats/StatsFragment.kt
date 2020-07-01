package com.souza.pokedetail.presentation.pokedetails.pokeattributes.stats

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.souza.pokedetail.databinding.FragmentPokeStatsBinding
import com.souza.pokedetail.domain.model.PokeProperty
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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
        val binding = FragmentPokeStatsBinding.inflate(layoutInflater)

        attackTextView = binding.textViewPokeAttackStat
        hpTextView = binding.textViewPokeHpStat
        defenseTextView = binding.textViewPokeDefenseStat
        specialAttackTextView = binding.textViewPokeSpecialAttackStat
        specialDefenseTextView = binding.textViewPokeSpecialDefenseStat
        speedTextView = binding.textViewPokeSpeedStat
        weightTextView = binding.textViewDetailWeightStat
        heightTextView = binding.textViewDetailHeightStat

        hpProgressBar = binding.progressBarHpStat
        attackProgressBar = binding.progressBarAttackStat
        defenseProgressBar = binding.progressBarDefenseStat
        specialAttackProgressBar = binding.progressBarSpecialAttackStat
        specialDefenseProgressBar = binding.progressBarSpecialDefenseStat
        speedProgressBar = binding.progressBarSpeedStat

        initObservers()

        return binding.root
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
