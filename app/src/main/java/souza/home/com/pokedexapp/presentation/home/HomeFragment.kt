package souza.home.com.pokedexapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import souza.home.com.extensions.gone
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.data.pokedex.HomePokedexStatus
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.presentation.details.DetailsFragment
import souza.home.com.pokedexapp.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW

class HomeFragment : Fragment() {

    private lateinit var pokesList : MutableList<Poke>
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var manager : FragmentManager
    private lateinit var progressBar : ProgressBar
    private lateinit var recyclerView : RecyclerView
    private lateinit var floatingActionButton : FloatingActionButton
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)
        pokesList = mutableListOf()
        progressBar = view.findViewById(R.id.progressBar)
        manager = activity?.supportFragmentManager!!
        bindViews(view)

        val  viewModel = ViewModelProviders.of(this,
            activity?.application?.let {
                HomeViewModel.Factory(
                    it
                )
            }
        )
            .get(HomeViewModel::class.java)

        initRecyclerView(viewModel)
        setFloactingActionPokeball()
        initObservers(viewModel)

        return view
    }

    private fun bindViews(view: View){
        adapter = HomeAdapter(pokesList, view.context)
        recyclerView = view.findViewById(R.id.poke_recycler_view)
        floatingActionButton = view.findViewById(R.id.image_view_floating_action_button_poke_ball)
    }

    private fun setFloactingActionPokeball(){
        floatingActionButton.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }
    }

    private fun initObservers(viewModel: HomeViewModel){
        viewModel.apply {
            this.updatePokesListOnViewLiveData().observe(viewLifecycleOwner, Observer {
                it?.toMutableList()?.let { pokesList -> adapter.submitList(pokesList) } })

            this.checkRequestStatus().observe(viewLifecycleOwner, Observer { toggleProgressBar(it) })
        }
    }

    private fun toggleProgressBar(it: HomePokedexStatus){
        when(it){
            HomePokedexStatus.DONE->{ turnOffProgressBar() }
            HomePokedexStatus.LOADING-> turnOnProgressBar()
            HomePokedexStatus.ERROR-> view?.let { it1 -> Snackbar.make(it1, getString(R.string.no_conectivity), 800).show() }
            else-> turnOffProgressBar()
        }
    }
    private fun initRecyclerView(viewModel : HomeViewModel){

        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        setTransitionToPokeDetails()

        setListenerRecyclerView(recyclerView, viewModel)
    }

    private fun setListenerRecyclerView(recyclerView: RecyclerView, viewModel: HomeViewModel){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                viewModel.onRecyclerViewScrolled(
                    dy = dy,
                    layoutManager = layoutManager
                )
            }
        })
    }

    private fun setTransitionToPokeDetails(){
        adapter.onItemClick = {

            val pokeId = it._id
            val pokeName = it.name
            val details = DetailsFragment(pokeId, pokeName)

            manager.beginTransaction().replace(R.id.nav_host_fragment, details).addToBackStack(null).commit()
        }
    }

    private fun turnOnProgressBar(){
        progressBar.visible()
    }

    private fun turnOffProgressBar(){
        progressBar.gone()
    }
}