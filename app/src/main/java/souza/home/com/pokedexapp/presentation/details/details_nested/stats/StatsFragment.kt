package souza.home.com.pokedexapp.presentation.details.details_nested.stats

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory


class StatsFragment(var pokemon: Int) : Fragment() {

    private lateinit var viewModel: PokeStatsViewModel
    private lateinit var tvHp : TextView
    private lateinit var pbHp : ProgressBar
    private lateinit var tvAttack : TextView
    private lateinit var pbAttack : ProgressBar
    private lateinit var tvDefense : TextView
    private lateinit var tvHeight : TextView
    private lateinit var tvWeight : TextView
    private lateinit var pbDefense : ProgressBar
    private lateinit var tvSpecialAttack: TextView
    private lateinit var pbSpecialAttack : ProgressBar
    private lateinit var tvSpecialDefense : TextView
    private lateinit var pbSpecialDefense : ProgressBar
    private lateinit var tvSpeed : TextView
    private lateinit var pbSpeed : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_stats, container, false)
        pbHp = view.findViewById(R.id.progress_bar_hp)
        pbAttack = view.findViewById(R.id.progress_bar_attack)
        pbDefense = view.findViewById(R.id.progress_bar_defense)
        pbSpecialAttack = view.findViewById(R.id.progress_bar_special_attack)
        pbSpecialDefense = view.findViewById(R.id.progress_bar_special_defense)
        pbSpeed = view.findViewById(R.id.progress_bar_speed)
        tvAttack = view.findViewById(R.id.text_view_poke_attack)
        tvHp = view.findViewById(R.id.text_view_poke_hp)
        tvDefense = view.findViewById(R.id.text_view_poke_defense)
        tvSpecialAttack = view.findViewById(R.id.text_view_poke_special_attack)
        tvSpecialDefense = view.findViewById(R.id.text_view_poke_special_deffense)
        tvSpeed = view.findViewById(R.id.text_view_poke_speed)
        tvWeight = view.findViewById(R.id.text_view_detail_weight)
        tvHeight = view.findViewById(R.id.text_view_detail_height)

        viewModel = ViewModelProviders.of(this,
            activity?.application?.let {
                NestedViewModelFactory(
                    pokemon,
                    it
                )
            }
        )
            .get(PokeStatsViewModel::class.java)

        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.updatePropertiesOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    initStats(it)
                }
            })
        }
    }

    private fun initStats(item: PokeProperty?){

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

    private fun animateStats(item: Int?, tv: TextView){
        val animator = ValueAnimator()
        animator.setObjectValues(0, item)
        animator.addUpdateListener {
                animation -> tv.text = animation.animatedValue.toString()
        }
        animator.duration = 600 // here you set the duration of the anim
        animator.start()
    }
}
