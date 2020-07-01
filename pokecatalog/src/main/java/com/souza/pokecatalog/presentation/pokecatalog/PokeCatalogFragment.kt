package com.souza.pokecatalog.presentation.pokecatalog

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.souza.connectivity.Connectivity
import com.souza.extensions.gone
import com.souza.extensions.visible
import com.souza.pokecatalog.R
import com.souza.pokecatalog.databinding.FragmentPokeCatalogBinding
import com.souza.pokecatalog.utils.Constants.Companion.ABSOLUTE_ZERO
import com.souza.pokecatalog.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsActivity
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PokeCatalogFragment : Fragment() {

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val connectivity by inject<Connectivity>()
    private lateinit var floatingActionButton: FloatingActionButton
    private val pokemonAdapter by inject<PokeCatalogAdapter>()
    private val viewModel by viewModel<PokeCatalogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPokeCatalogBinding.inflate(layoutInflater)

        progressBar = binding.progressBarHome
        recyclerView = binding.recyclerViewHome
        floatingActionButton = binding.floatingActionButtonPokeBallHome

        val toolbar = binding.pokedexToolbarHome
        setToolbarBackButton(toolbar)
        viewModel.getPokes()
        setupRecyclerView()
        setupFloatingActionPokeball()
        initObservers()
        initConnectivityObserver()

        return binding.root
    }

    private fun setupFloatingActionPokeball() {
        floatingActionButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(ABSOLUTE_ZERO)
        }
    }

    private fun setToolbarBackButton(toolbar: Toolbar) {
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    private fun initObservers() {
        viewModel.apply {
            this.updatePokesListOnViewLiveData().observe(viewLifecycleOwner, Observer {
                it?.toMutableList()?.let { pokes -> pokemonAdapter.submitList(pokes) }
            })
            this.turnOffProgressBarOnLiveData().observe(viewLifecycleOwner, Observer { turnOffProgressBar() })

            this.turnOnProgressBarOnLiveData().observe(viewLifecycleOwner, Observer { turnOnProgressBar() })

            this.checkEndOfList.observe(viewLifecycleOwner, Observer { turnOnEndListMessage() })
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(viewLifecycleOwner, Observer { hasNetworkConnectivity ->
            viewModel.updateConnectivityStatus(hasNetworkConnectivity = hasNetworkConnectivity)
        })
    }

    private fun setupRecyclerView() {
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pokemonAdapter
        setupTransitionToPokeDetails()

        setupRecyclerViewEndlessScroll(recyclerView)
    }

    private fun setupRecyclerViewEndlessScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.loadOnRecyclerViewScrolled(
                    dy = dy,
                    layoutManager = layoutManager
                )
            }
        })
    }

    private fun setupTransitionToPokeDetails() {
        pokemonAdapter.onItemClick = {
            val pokeId = it.id
            val pokeName = it.name

            val intent = Intent(activity, PokeDetailsActivity::class.java)
            intent.putExtra("pokeId", pokeId)
            intent.putExtra("pokeName", pokeName)
            activity?.startActivity(intent)
        }
    }

    private fun turnOnProgressBar() {
        progressBar.visible()
    }

    private fun turnOffProgressBar() {
        progressBar.gone()
    }

    private fun turnOnEndListMessage() {
        progressBar.gone()
        Snackbar.make(requireView(), getString(R.string.snackbar_message_end_of_the_list), BaseTransientBottomBar.LENGTH_SHORT).show()
    }
}
