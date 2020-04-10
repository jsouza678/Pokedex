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

import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.varieties.PokeVarieties
import souza.home.com.pokedexapp.presentation.details.DetailsPokedexFragment
import souza.home.com.pokedexapp.presentation.details.details_nested.PokedexViewModelFactory


class PokeAboutFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeAboutViewModel
    private lateinit var poke: String
    private lateinit var spVariations : Spinner
    private lateinit var tvDesc : TextView

    private lateinit var varietiesArray: MutableList<PokeVarieties>
    private lateinit var pokemonsArray: MutableList<PokeVarieties>
    private var language : String = "en"


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
        viewModel = ViewModelProviders.of(this,
            PokedexViewModelFactory(
                pokemon,
                activity!!.application
            )
        )
            .get(PokeAboutViewModel::class.java)

        tvDesc = view.findViewById(R.id.tv_poke_desc)
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
            this.updateVariationsOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    initSpinner()
                    adapterSpinner.submitList(it.varieties)
                    pokemonsArray = it.varieties!!
                    var description : String = ""
                    for(i in 0 until it.description?.size!!) {
                        if (it.description?.get(i)?.language?.name == language) {
                            description += it.description!![i].flavor_text + "\n "
                        }
                    }
                    tvDesc.text = description
                }

            })
           /* this.varietiesResponse.observe(viewLifecycleOwner, Observer {
                    initSpinner()
                    adapterSpinner.submitList(it.varieties)
                    pokemonsArray = it.varieties
                    var description : String = ""
                    for(i in 0 until it.description.size) {
                        if (it.description[i].language.name == language) {
                            description += it.description[i].flavor_text + "\n "
                        }
                    }
                    tvDesc.text = description
            })*/
        }
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
                        //Reload Fragment
                        urlChain = pokemonsArray[spinnerSelected].pokemon._id

                        pokePath = urlChain.substringAfterLast("n/").substringBeforeLast("/")

                        if(pokePath.trim() == pokemon.trim()){
                            Toast.makeText(context, "Same poke", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "ReloadScreen", Toast.LENGTH_SHORT).show()

                            val newPoke = pokemonsArray[spinnerSelected].pokemon.name

                            val details = DetailsPokedexFragment(pokePath, newPoke)

                            fragmentManager!!.beginTransaction().replace(R.id.nav_host_fragment, details).commit()
                        }
                    }
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing when not selected"
            }

        }

    }
}