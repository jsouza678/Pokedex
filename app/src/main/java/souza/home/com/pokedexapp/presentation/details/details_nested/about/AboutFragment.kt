package souza.home.com.pokedexapp.presentation.details.details_nested.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.PokeVarieties
import souza.home.com.pokedexapp.presentation.details.DetailsFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING
import souza.home.com.pokedexapp.utils.cropPokeUrl

class AboutFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeAboutViewModel
    private lateinit var poke: String
    private lateinit var spVariations : Spinner
    private lateinit var tvDesc : TextView
    private lateinit var varietiesArray: MutableList<PokeVarieties>
    private lateinit var pokemonsArray: MutableList<PokeVarieties>
    private lateinit var adapterSpinner : SpinnerAdapter
    private var pokePath : String = EMPTY_STRING
    private var urlChain : String = EMPTY_STRING
    private var spinnerSelected: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_about, container, false)
        tvDesc = view.findViewById(R.id.tv_poke_desc)
        spVariations = view.findViewById(R.id.spinner_variations)

        poke = pokemon
        viewModel = ViewModelProviders.of(this,
            NestedViewModelFactory(
                pokemon,
                activity!!.application
            )
        )
            .get(PokeAboutViewModel::class.java)

        varietiesArray = ArrayList()
        pokemonsArray = ArrayList()

        adapterSpinner =
            SpinnerAdapter(
                view.context,
                pokemonsArray
            )

        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.updateVariationsOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    initSpinner()
                    adapterSpinner.submitList(it.varieties)
                    pokemonsArray = it.varieties!!
                    tvDesc.text = it.description
                }

            })
        }
    }

    private fun initSpinner() {

        spVariations.adapter = adapterSpinner

        spVariations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                spinnerSelected = position - 1
                when(position){
                    0 ->  {
                    }
                    else-> {
                        //Reload Fragment
                        urlChain = pokemonsArray[spinnerSelected].pokemon._id

                        pokePath = cropPokeUrl(urlChain)

                        if(pokePath == pokemon){
                            Toast.makeText(context, "Same poke", Toast.LENGTH_SHORT).show()
                        }else{
                            view?.let { Snackbar.make(it, R.string.snackbar_loading_poke_evolution, BaseTransientBottomBar.LENGTH_SHORT).show() }
                            val newPoke = pokemonsArray[spinnerSelected].pokemon.name
                            val details = DetailsFragment(pokePath, newPoke)

                            fragmentManager?.beginTransaction()
                                ?.replace(R.id.nav_host_fragment, details)?.commit()
                        }
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {/* Do nothing when not selected" */ }
        }

    }
}