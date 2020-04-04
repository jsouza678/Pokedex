package souza.home.com.pokedexapp.ui.details.other

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.stats.PokeAbilities
import souza.home.com.pokedexapp.network.model.stats.PokeTypes
import souza.home.com.pokedexapp.ui.details.PokedexViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class PokeOthersFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeOthersViewModel
    private lateinit var poke: String
    private lateinit var lvAbilities : ListView
    private lateinit var lvTypes : ListView
    private lateinit var adapterTypes: CustomTypeAdapter
    private lateinit var adapterAbilities: CustomAbilityAdapter
    private lateinit var typesArray: MutableList<PokeTypes>
    private lateinit var abilitiesArray: MutableList<PokeAbilities>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_others, container, false)
        poke = pokemon

        lvTypes = view.findViewById(R.id.lv_types)
        lvAbilities = view.findViewById(R.id.lv_abilities)
        typesArray = ArrayList()
        abilitiesArray = ArrayList()

        adapterTypes =
            CustomTypeAdapter(
                view.context,
                typesArray
            )
        adapterAbilities =
            CustomAbilityAdapter(
                view.context,
                abilitiesArray
            )


        viewModel = ViewModelProviders.of(this, PokedexViewModelFactory(pokemon, activity!!.application))
            .get(PokeOthersViewModel::class.java)

        initType()
        initAbilities()
        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.other.observe(viewLifecycleOwner, Observer {
                if(it!=null){

                    adapterTypes.submitList(it.types)
                    adapterAbilities.submitList(it.abilities)

                }
            })}
    }

    private fun initType(){
        lvTypes.adapter = adapterTypes
    }

    private fun initAbilities(){
        lvAbilities.adapter = adapterAbilities
    }

}
