package souza.home.com.pokedexapp.ui.home


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
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import souza.home.com.pokedexapp.ui.details.DetailsPokedexFragment
import souza.home.com.pokedexapp.ui.extensions.gone
import souza.home.com.pokedexapp.ui.extensions.visible


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
        pokesList = ArrayList()

        val view = inflater.inflate(R.layout.fragment_home_pokedex, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        manager = activity!!.supportFragmentManager
        adapter = PokesAdapter(pokesList, view.context)

        recyclerView = view.findViewById(R.id.poke_recycler_view)

        val viewModel =  ViewModelProviders.of(this)
            .get(HomePokedexViewModel::class.java)

        initObservers(viewModel)


        return view
    }

    private fun initObservers(viewModel: HomePokedexViewModel){
        viewModel.apply {
            this.poke.observe(viewLifecycleOwner, Observer {
                    initRecyclerView(viewModel)
                    pokesList = viewModel.poke.value!!
                    //adapter.submitList(pokesList)
            })

            this.status.observe(viewLifecycleOwner, Observer {
                when(it){
                    HomePokedexStatus.DONE->{
                        adapter.submitList(pokesList)
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
