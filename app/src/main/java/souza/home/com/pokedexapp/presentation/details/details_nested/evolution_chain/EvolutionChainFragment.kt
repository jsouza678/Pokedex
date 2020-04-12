package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.Evolution
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory
import souza.home.com.pokedexapp.utils.cropPokeUrl

class EvolutionChainFragment(var pokemon: Int) : Fragment() {

    private lateinit var viewModel: EvolutionChainViewModel
    private lateinit var lvChain : ListView
    private lateinit var adapterChain : EvolutionChainAdapter
    private lateinit var evolutionArray: MutableList<Evolution>
    private lateinit var listString : MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_chain, container, false)
        lvChain = view.findViewById(R.id.list_view_chain)
        listString = mutableListOf<String>()
        viewModel = ViewModelProviders.of(this,
            NestedViewModelFactory(
                pokemon,
                activity!!.application
            )
        )
            .get(EvolutionChainViewModel::class.java)

        evolutionArray = mutableListOf<Evolution>()
        evolutionArray.clear()

        listString = mutableListOf()

        adapterChain =
            EvolutionChainAdapter(
                view.context,
                listString
            )

        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {

            this.updateVariationsOnViewLiveData()?.observe(this@EvolutionChainFragment, Observer {
                    val evolutionPath = it.evolution_chain?.url
                    val evolutionCropped = evolutionPath?.let { url -> cropPokeUrl(url) }

                evolutionCropped?.let { it1 -> initEvolutionChainViewModel(it1) }
            })
        }
    }

    private fun initEvolutionChainViewModel(evolutionCropped: String){
        val viewModel = ViewModelProviders.of(this@EvolutionChainFragment,
            NestedViewModelFactory(
                Integer.parseInt(evolutionCropped),
                activity!!.application
            )
        )
            .get(EvolutionsViewModel::class.java)

        initSecondaryObserver(viewModel)
    }

    private fun initSecondaryObserver(viewModel: EvolutionsViewModel){
        viewModel.updateEvolutionOnViewLiveData()?.observe(this@EvolutionChainFragment, Observer {
            if(it!=null){
                listString = it.evolution!!
                initChainEvolution()
                adapterChain.submitList(listString)
            }
        })
    }

    private fun initChainEvolution(){
        lvChain.adapter = adapterChain
    }
}