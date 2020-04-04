package souza.home.com.pokedexapp.ui.details.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.varieties.PokeVarieties
import souza.home.com.pokedexapp.ui.details.PokedexViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class PokeAboutFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeAboutViewModel
    private lateinit var poke: String
    private lateinit var spVariations : Spinner

    private lateinit var varietiesArray: MutableList<PokeVarieties>
    private lateinit var pokemonsArray: MutableList<PokeVarieties>



    private lateinit var adapterSpinner : CustomSpinnerAdapter

    private var pokePath : String = ""
    private var urlChain : String = ""
    private var spinnerSelected: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_about, container, false)

        poke = pokemon
        viewModel = ViewModelProviders.of(this, PokedexViewModelFactory(pokemon, activity!!.application))
            .get(PokeAboutViewModel::class.java)


        spVariations = view.findViewById(R.id.spinner_variations)


        varietiesArray = ArrayList()
        pokemonsArray = ArrayList()


        adapterSpinner =
            CustomSpinnerAdapter(
                view.context,
                pokemonsArray
            )


        initObservers()


        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.varieties.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    initSpinner()
                    adapterSpinner.submitList(it.varieties)
                    pokemonsArray = it.varieties
                }
            })}
    }


    private fun initSpinner() {

        spVariations.adapter = adapterSpinner

        spVariations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                spinnerSelected = position-1
                when(position){
                    0 ->  {

                    }
                    else-> {
                        urlChain = pokemonsArray[spinnerSelected].pokemon.url

                        pokePath = urlChain.substringAfterLast("n/").substringBeforeLast("/")

                        //Toast.makeText(context, pokePath, Toast.LENGTH_SHORT).show()

                        if(pokePath.trim() == pokemon.trim()){
                            Toast.makeText(context, "Same poke", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "ReloadScreen", Toast.LENGTH_SHORT).show()
                            //reload the fragment
                            //fragmentManager!!.beginTransaction().replace(R.id.nav_host_fragment, DetailsPokedexFragment(pokePath)).commit()

                            //need to call everything at selection.
                        }
                    }
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing when not selected. maybe a "
            }

        }

    }
}