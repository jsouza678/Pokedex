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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import souza.home.com.pokedexapp.R
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import souza.home.com.pokedexapp.ui.details.DetailsPokedexFragment


class HomePokedexFragment : Fragment() {

    private lateinit var pokesList : MutableList<Pokemon>
    private lateinit var layoutManager: GridLayoutManager
    //private val detailsPoke =  DetailsPokedexFragment()
    private lateinit var manager : FragmentManager
    private lateinit var progressBar : ProgressBar
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: PokesAdapter
    private var page = 0

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
                if(it!=null){
                    initRecyclerView(viewModel)
                    pokesList = viewModel.poke.value!!
                    adapter.submitList(pokesList)

                  /*  if(pokesList.size == 0){
                        //set empty screen after
                    }else{

                    }*/
                }


            })

            this.status.observe(viewLifecycleOwner, Observer {
                when(it){
                    HomePokedexStatus.DONE->
                        progressBar.visibility = View.GONE
                    HomePokedexStatus.LOADING->
                        progressBar.visibility = View.VISIBLE
                    HomePokedexStatus.ERROR->
                        Toast.makeText(context, "No conectivity", Toast.LENGTH_SHORT).show()
                    else->
                        progressBar.visibility = View.GONE

                }
            })
        }


    }

    private fun initRecyclerView(viewModel : HomePokedexViewModel){

        layoutManager = GridLayoutManager(context, 2)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = adapter
/*        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                GridLayoutManager.VERTICAL)
        )*/

       setTransitionToPokeDetails()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                val total = adapter.itemCount

                if (!viewModel.isLoading) {

                    if ((visibleItemCount + pastVisibleItem) >= total) {
                        page+=20
                        val auxList = viewModel.getPage(page)
                        adapter.submitList(auxList)
                        //////////////////////
                    }

                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setTransitionToPokeDetails(){
        adapter.onItemClick = {

            val urlChain = it.url
            val pokePath = urlChain?.substringAfterLast("n/")

            val details = DetailsPokedexFragment(pokePath)

            manager.beginTransaction().replace(R.id.nav_host_fragment, details).addToBackStack(null).commit()

        }

    }




}
