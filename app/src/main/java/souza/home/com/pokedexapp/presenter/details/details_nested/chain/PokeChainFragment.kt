package souza.home.com.pokedexapp.presenter.details.details_nested.chain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.presenter.details.details_nested.PokedexViewModelFactory

class PokeChainFragment(var pokemon: String) : Fragment() {

    private lateinit var viewModel: PokeChainViewModel
    private lateinit var poke: String
    private lateinit var lvChain : ListView
    private lateinit var adapterChain : CustomChainAdapter
    private lateinit var evolutionArray: MutableList<PokeEvolution>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_poke_chain, container, false)
        poke = pokemon

        viewModel = ViewModelProviders.of(this,
            PokedexViewModelFactory(
                pokemon,
                activity!!.application
            )
        )
            .get(PokeChainViewModel::class.java)

        evolutionArray = ArrayList()
        lvChain = view.findViewById(R.id.lv_chain)

        adapterChain =
            CustomChainAdapter(
                view.context,
                evolutionArray
            )

        initChainEvolution()
        initObservers()

        return view
    }

    private fun initObservers(){
        viewModel.apply {
            this.chain.observe(viewLifecycleOwner, Observer {
                if(it!=null){
                    adapterChain.submitList(it)
                }
            })}
    }

    private fun initChainEvolution(){
        lvChain.adapter = adapterChain
    }


}