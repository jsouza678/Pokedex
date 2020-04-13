package souza.home.com.pokedexapp.presentation.details.details_nested.evolution_chain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.Evolution
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory

class EvolutionChainFragment(var pokemon: Int) : Fragment() {

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
        bindViews(view)
        listString = mutableListOf<String>()

        evolutionArray = mutableListOf<Evolution>()
        evolutionArray.clear()

        listString = mutableListOf()

        adapterChain =
            EvolutionChainAdapter(
                view.context,
                listString
            )

        initEvolutionChainViewModel(pokemon)

        return view
    }

    private fun bindViews(view: View){
        lvChain = view.findViewById(R.id.list_view_chain)
    }

    private fun initEvolutionChainViewModel(evolutionCropped: Int){
        val viewModel = ViewModelProviders.of(this@EvolutionChainFragment,
            NestedViewModelFactory(
                evolutionCropped,
                activity!!.application
            )
        )
            .get(EvolutionChainViewModel::class.java)

        initSecondaryObserver(viewModel)
    }

    private fun initSecondaryObserver(viewModel: EvolutionChainViewModel){
        viewModel.updateEvolutionOnViewLiveData()?.observe(viewLifecycleOwner, Observer {
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