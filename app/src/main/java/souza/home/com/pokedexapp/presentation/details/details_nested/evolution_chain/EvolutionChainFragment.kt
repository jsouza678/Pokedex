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
import souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.presentation.details.details_nested.NestedViewModelFactory

class EvolutionChainFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeChainViewModel
    private lateinit var poke: String
    private lateinit var lvChain : ListView
    private lateinit var adapterChain : EvolutionChainAdapter
    private lateinit var evolutionArray: MutableList<PokeEvolution>
    private var evolutionPath: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_chain, container, false)
        poke = pokemon

        viewModel = ViewModelProviders.of(this,
            NestedViewModelFactory(
                pokemon,
                activity!!.application
            )
        )
            .get(PokeChainViewModel::class.java)

        evolutionArray = ArrayList()
        lvChain = view.findViewById(R.id.lv_chain)

        adapterChain =
            EvolutionChainAdapter(
                view.context,
                evolutionArray
            )

        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {

            this.updateVariationsOnViewLiveData()?.observe(this@EvolutionChainFragment, Observer {
                evolutionPath = it.evolution_chain?.url
                evolutionPath?.let { it1 -> loadEvolutionChain(it1) }
            })
            this.chain.observe(viewLifecycleOwner, Observer {
                initChainEvolution()
                adapterChain.submitList(it)
            })

            this.status.observe(this@EvolutionChainFragment, Observer {
                if(it == DetailsPokedexStatus.DONE){

                }
            })
        }
    }

    private fun initChainEvolution(){
        lvChain.adapter = adapterChain
    }


}