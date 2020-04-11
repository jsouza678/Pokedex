package souza.home.com.pokedexapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.presentation.details.DetailsFragment
import souza.home.com.extensions.gone
import souza.home.com.extensions.visible
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.utils.cropPokeUrl

class HomeFragment : Fragment() {

    private lateinit var pokesList : MutableList<Poke>
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var manager : FragmentManager
    private lateinit var progressBar : ProgressBar
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        pokesList = mutableListOf()

        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        manager = activity!!.supportFragmentManager
        adapter = HomeAdapter(pokesList, view.context)
        recyclerView = view.findViewById(R.id.poke_recycler_view)

        val  viewModel = ViewModelProviders.of(this,
            HomePokedexViewModel.Factory(
                activity!!.application
            )
        )
            .get(HomePokedexViewModel::class.java)

        initRecyclerView(viewModel)
        initObservers(viewModel)

        return view
    }

    private fun initObservers(viewModel: HomePokedexViewModel){
        viewModel.apply {

            this.updatePokeslListOnViewLiveData().observe(this@HomeFragment, Observer {
                it?.toMutableList()?.let { it1 -> adapter.submitList(it1) }
            })

            this.status.observe(viewLifecycleOwner, Observer {
                when(it){
                    HomePokedexStatus.DONE->{
                        turnOffProgressBar()
                    }
                    HomePokedexStatus.LOADING->
                        turnOnProgressBar()
                    HomePokedexStatus.ERROR->
                        Toast.makeText(context, "No conectivity", Toast.LENGTH_SHORT).show()
                    else->
                        turnOffProgressBar()
                }
            })
        }
    }

    private fun initRecyclerView(viewModel : HomePokedexViewModel){

        layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        setTransitionToPokeDetails()

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
