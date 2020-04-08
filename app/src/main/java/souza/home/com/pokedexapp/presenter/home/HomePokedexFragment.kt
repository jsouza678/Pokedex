package souza.home.com.pokedexapp.presenter.home


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import souza.home.com.pokedexapp.presenter.details.DetailsPokedexFragment
import souza.home.com.pokedexapp.extensions.gone
import souza.home.com.pokedexapp.extensions.visible
import souza.home.com.pokedexapp.presenter.details.DetailsPokedexViewModel
import souza.home.com.pokedexapp.presenter.details.DetailsPokedexViewModelFactory


class HomePokedexFragment : Fragment() {

    private lateinit var pokesList : MutableList<Pokemon>
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var manager : FragmentManager
    private lateinit var progressBar : ProgressBar
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: PokesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        pokesList = mutableListOf()

        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        manager = activity!!.supportFragmentManager
        adapter = PokesAdapter(pokesList, view.context)

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

            this.updatePokeslListOnViewLiveData().observe(this@HomePokedexFragment, Observer {

                adapter.submitList(it.toMutableList())
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

            val urlChain = it.url
            val pokeName = it.name
            val pokePath = urlChain.substringAfterLast("n/").substringBeforeLast("/")
            val details = DetailsPokedexFragment(pokePath, pokeName)

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
