package souza.home.com.pokedexapp.ui.details.stats

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import souza.home.com.pokedexapp.ui.details.PokedexViewModelFactory


class PokeStatsFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeStatsViewModel
    //private lateinit var poke: String
    private lateinit var tvHp : TextView
    private lateinit var tvAttack : TextView
    private lateinit var tvDeffense : TextView
    private lateinit var tvSpecialAttack: TextView
    private lateinit var tvSpecialDefense : TextView
    private lateinit var tvSpeed : TextView
    private var menuVisible: Boolean = false
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_stats, container, false)

        viewModel = ViewModelProviders.of(this, PokedexViewModelFactory(pokemon, activity!!.application))
            .get(PokeStatsViewModel::class.java)
        tvHp = view.findViewById(R.id.tv_poke_hp)
        tvAttack = view.findViewById(R.id.tv_poke_attack)
        tvDeffense = view.findViewById(R.id.tv_poke_deffense)
        tvSpecialAttack = view.findViewById(R.id.tv_poke_special_attack)
        tvSpecialDefense = view.findViewById(R.id.tv_poke_special_deffense)
        tvSpeed = view.findViewById(R.id.tv_poke_speed)

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.stats.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    initStats(it)
                }
            })}
    }

    private fun initStats(item: PokemonProperty){
        animateStats(Integer.valueOf(item.stats[5].base_stat), tvHp)
        animateStats(Integer.valueOf(item.stats[4].base_stat), tvAttack)
        animateStats(Integer.valueOf(item.stats[3].base_stat), tvDeffense)
        animateStats(Integer.valueOf(item.stats[2].base_stat), tvSpecialAttack)
        animateStats(Integer.valueOf(item.stats[1].base_stat), tvSpecialDefense)
        animateStats(Integer.valueOf(item.stats[0].base_stat), tvSpeed)
    }

    private fun animateStats(item: Int, tv: TextView){
        val animator = ValueAnimator()
        animator.setObjectValues(0, item)// here you set the range, from 0 to "count" value
        animator.addUpdateListener {
                animation -> tv.text = animation.animatedValue.toString()
        }
        animator.duration = 600 // here you set the duration of the anim
        animator.start()
    }

    //Get the moment when the fragment is visible
    override fun setMenuVisibility(visible: Boolean){
        super.setMenuVisibility(visible)
        if (visible) {
            if(count==0){ // this prevents Observer to be called everytime the fragment is visible
                initObservers()
                count=1
            }
        } else {
            menuVisible = false
        }
    }


}
