package com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.souza.extensions.observeOnce
import com.souza.pokedetail.R
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain.EvolutionChainAdapter
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EvolutionChainFragment(private val pokemonId: Int) : Fragment() {

    private lateinit var evolutionChainListView: ListView
    private var evolutionChain: MutableList<String> = mutableListOf()
    private val adapterChain by inject<EvolutionChainAdapter> { parametersOf(evolutionChain) }
    private val viewModel by viewModel<EvolutionChainViewModel> { parametersOf(pokemonId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_poke_chain, container, false)
        bindViews(view)
        initObserver()
        initChainEvolution()

        return view
    }

    private fun bindViews(view: View) {
        evolutionChainListView = view.findViewById(R.id.list_view_chain)
    }

    private fun initObserver() {
        viewModel.updateEvolutionOnViewLiveData()?.observeOnce(viewLifecycleOwner, Observer {
            evolutionChain = it.evolution!!
            adapterChain.submitList(evolutionChain)
        })
    }

    private fun initChainEvolution() {
        evolutionChainListView.adapter = adapterChain
    }
}
