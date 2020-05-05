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
import org.koin.android.viewmodel.ext.android.viewModel
import souza.home.com.extensions.gone
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.HomePokedexStatus
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.presentation.pokedetail.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.ABSOLUTE_ZERO
import souza.home.com.pokedexapp.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW

class PokeCatalogFragment : Fragment() {

    private lateinit var pokesList: MutableList<Poke>
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var adapter: PokeCatalogAdapter
    private lateinit var toolbarHomeTop: Toolbar
    private val viewModel by viewModel<PokeCatalogViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)
        pokesList = mutableListOf()
        progressBar = view.findViewById(R.id.progress_bar_home)
        bindViews(view)

        initRecyclerView(viewModel)
        setFloactingActionPokeball()
        initObservers(viewModel)

        return view
    }

    private fun bindViews(view: View) {
        adapter = PokeCatalogAdapter(pokesList, view.context)
        recyclerView = view.findViewById(R.id.recycler_view_home)
        floatingActionButton = view.findViewById<FloatingActionButton>(R.id.floating_action_button_poke_ball_home)
        toolbarHomeTop = view.findViewById(R.id.pokedex_toolbar_home)
    }

    private fun setFloactingActionPokeball() {
        floatingActionButton.setOnClickListener {
            recyclerView.smoothScrollToPosition(ABSOLUTE_ZERO)
        }
    }

    private fun initObservers(viewModel: PokeCatalogViewModel) {
        viewModel.apply {
            this.updatePokesListOnViewLiveData().observe(viewLifecycleOwner, Observer {
                it?.toMutableList()?.let { pokesList -> adapter.submitList(pokesList)
                }
            })
        }
    }

    private fun toggleProgressBar(it: HomePokedexStatus) {
        when (it) {
            HomePokedexStatus.DONE -> { turnOffProgressBar() }
            HomePokedexStatus.LOADING -> turnOnProgressBar()
            HomePokedexStatus.ERROR -> view?.let { itemView -> Snackbar.make(itemView, getString(R.string.no_conectivity), BaseTransientBottomBar.LENGTH_SHORT).show() }
        }
    }

    private fun initRecyclerView(viewModel: PokeCatalogViewModel) {

        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        setTransitionToPokeDetails()

        setListenerRecyclerView(recyclerView, viewModel)
    }

    private fun setListenerRecyclerView(recyclerView: RecyclerView, viewModel: PokeCatalogViewModel) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.onRecyclerViewScrolled(
                    dy = dy,
                    layoutManager = layoutManager
                )
            }
        })
    }

    private fun setTransitionToPokeDetails() {
        adapter.onItemClick = {

            val pokeId = it._id
            val pokeName = it.name
            val details = DetailsFragment(pokeId, pokeName)

            fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment_home_activity, details)?.addToBackStack(null)?.commit()
        }
    }

    private fun turnOnProgressBar() {
        progressBar.visible()
    }

    private fun turnOffProgressBar() {
        progressBar.gone()
    }
}
