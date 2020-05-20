package souza.home.com.pokedexapp.presentation.pokecatalog

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
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import souza.home.com.connectivity.Connectivity
import souza.home.com.extensions.gone
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.presentation.pokedetails.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW

class PokeCatalogFragment : Fragment() {

    private lateinit var pokemons: MutableList<Pokemon>
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private val connectivity by inject<Connectivity>()
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var pokemonAdapter: PokeCatalogAdapter
    private lateinit var toolbarHomeTop: Toolbar
    private val viewModel by viewModel<PokeCatalogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)
        pokemons = mutableListOf()
        progressBar = view.findViewById(R.id.progress_bar_home)
        bindViews(view)

        setupRecyclerView(viewModel)
        setupFloactingActionPokeball()
        initObservers(view)
        initConnectivityObserver()

        return view
    }

    private fun bindViews(view: View) {
        pokemonAdapter = PokeCatalogAdapter(pokemons, view.context)
        recyclerView = view.findViewById(R.id.recycler_view_home)
        floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floating_action_button_poke_ball_home)
        toolbarHomeTop = view.findViewById(R.id.pokedex_toolbar_home)
    }

    private fun setupFloactingActionPokeball() {
        floatingActionButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(ABSOLUTE_ZERO)
        }
    }

    private fun initObservers(view: View) {
        viewModel.apply {
            this.updatePokesListOnViewLiveData().observe(viewLifecycleOwner, Observer {
                it?.toMutableList()?.let { pokes -> pokemonAdapter.submitList(pokes) }
            })
            this.turnOffProgressBar.observe(viewLifecycleOwner, Observer { turnOffProgressBar() })

            this.turnOnProgressBar.observe(viewLifecycleOwner, Observer { turnOnProgressBar() })

            this.checkEndOfList.observe(viewLifecycleOwner, Observer { turnOnEndListMessage(view) })
        }
    }

    private fun initConnectivityObserver() {
        connectivity.observe(viewLifecycleOwner, Observer { hasNetworkConnectivity ->
            viewModel.updateConnectivityStatus(hasNetworkConnectivity = hasNetworkConnectivity)
        })
    }

    private fun setupRecyclerView(viewModel: PokeCatalogViewModel) {

        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pokemonAdapter
        setupTransitionToPokeDetails()

        setupRecyclerViewEndlessScroll(recyclerView, viewModel)
    }

    private fun setupRecyclerViewEndlessScroll(recyclerView: RecyclerView, viewModel: PokeCatalogViewModel) {
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
            val details = pokeName?.let { pokemonName -> DetailsFragment(pokeId, pokemonName) }

            details?.let { pokemonName ->
                fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_home_activity,
                    pokemonName
                )?.addToBackStack(null)?.commit()
            }
        }
    }

    private fun turnOnProgressBar() {
        progressBar.visible()
    }

    private fun turnOffProgressBar() {
        progressBar.gone()
    }

    private fun turnOnEndListMessage(view: View) {
        progressBar.gone()
        Snackbar.make(view, getString(R.string.snackbar_message_end_of_the_list), BaseTransientBottomBar.LENGTH_SHORT).show()
    }
}
