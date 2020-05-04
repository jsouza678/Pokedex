package souza.home.com.pokedexapp.presentation.detailsfragment.details_nested.stats

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

class StatsFragment(private val pokemon: Int) : Fragment() {

    private val viewModel by viewModel<StatsViewModel>{ parametersOf(pokemon)}
    private lateinit var tvHp: TextView
    private lateinit var pbHp: ProgressBar
    private lateinit var tvAttack: TextView
    private lateinit var pbAttack: ProgressBar
    private lateinit var tvDefense: TextView
    private lateinit var tvHeight: TextView
    private lateinit var tvWeight: TextView
    private lateinit var pbDefense: ProgressBar
    private lateinit var tvSpecialAttack: TextView
    private lateinit var pbSpecialAttack: ProgressBar
    private lateinit var tvSpecialDefense: TextView
    private lateinit var pbSpecialDefense: ProgressBar
    private lateinit var tvSpeed: TextView
    private lateinit var pbSpeed: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_stats, container, false)
        bindViews(view)
        initObservers()

        return view
    }

    private fun bindViews(view: View) {
        pbHp = view.findViewById(R.id.progress_bar_hp_stat)
        pbAttack = view.findViewById(R.id.progress_bar_attack_stat)
        pbDefense = view.findViewById(R.id.progress_bar_defense_stat)
        pbSpecialAttack = view.findViewById(R.id.progress_bar_special_attack_stat)
        pbSpecialDefense = view.findViewById(R.id.progress_bar_special_defense_stat)
        pbSpeed = view.findViewById(R.id.progress_bar_speed_stat)
        tvAttack = view.findViewById(R.id.text_view_poke_attack_stat)
        tvHp = view.findViewById(R.id.text_view_poke_hp_stat)
        tvDefense = view.findViewById(R.id.text_view_poke_defense_stat)
        tvSpecialAttack = view.findViewById(R.id.text_view_poke_special_attack_stat)
        tvSpecialDefense = view.findViewById(R.id.text_view_poke_special_defense_stat)
        tvSpeed = view.findViewById(R.id.text_view_poke_speed_stat)
        tvWeight = view.findViewById(R.id.text_view_detail_weight_stat)
        tvHeight = view.findViewById(R.id.text_view_detail_height_stat)
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

        item?.stats?.get(5)?.base_stat.let { it?.let { it1 -> Integer.valueOf(it1) } }?.let { animateStats(it, tvHp) }
        pbHp.progress = item?.stats?.get(5)?.base_stat?.let { Integer.parseInt(it) }!!

        item.stats?.get(4)?.base_stat?.let { Integer.valueOf(it) }?.let { animateStats(it, tvAttack) }
        pbAttack.progress = item.stats?.get(4)?.base_stat?.let { Integer.parseInt(it) }!!

        item.stats?.get(3)?.base_stat?.let { Integer.valueOf(it) }?.let { animateStats(it, tvDefense) }
        pbDefense.progress = item.stats?.get(3)?.base_stat?.let { Integer.parseInt(it) }!!

        item.stats?.get(2)?.base_stat?.let { Integer.valueOf(it) }?.let { animateStats(it, tvSpecialAttack) }
        pbSpecialAttack.progress = item.stats?.get(2)?.base_stat?.let { Integer.parseInt(it) }!!

        item.stats?.get(1)?.base_stat?.let { Integer.valueOf(it) }?.let { animateStats(it, tvSpecialDefense) }
        pbSpecialDefense.progress = item.stats?.get(1)?.base_stat?.let { Integer.parseInt(it) }!!

        item.stats?.get(0)?.base_stat?.let { Integer.valueOf(it) }?.let { animateStats(it, tvSpeed) }
        pbSpeed.progress = item.stats?.get(0)?.base_stat?.let { Integer.parseInt(it) }!!

        animateStats(item.weight?.let { Integer.valueOf(it) }, tvWeight)
        animateStats(item.height?.let { Integer.valueOf(it) }, tvHeight)
    }

    private fun animateStats(item: Int?, tv: TextView) {
        val animator = ValueAnimator()
        animator.setObjectValues(0, item)
        animator.addUpdateListener {
                animation -> tv.text = animation.animatedValue.toString()
        }
        animator.duration = 600 // here you set the duration of the anim
        animator.start()
    }
}
