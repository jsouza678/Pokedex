package souza.home.com.pokedexapp.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home_pokedex.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import souza.home.com.pokedexapp.databinding.FragmentHomePokedexBinding
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.main_model.Pokemon
import souza.home.com.pokedexapp.network.PokeRootProperty




/**
 * A simple [Fragment] subclass.
 */
class HomePokedex : Fragment() {

    lateinit var layoutManager: LinearLayoutManager
    var page = 0
    var isLoading = false
    var numberList: MutableList<Pokemon> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomePokedexBinding.inflate(inflater)

        loadFirstPage(binding.root.context)

        //var searchBar = binding.searchBar
        //var searchText = searchBar.text


        return binding.root
    }


    private fun loadFirstPage(context: Context){

        PokeApi.retrofitService.getPokes(page)
            .enqueue(object : Callback<PokeRootProperty> {
                override fun onFailure(call: Call<PokeRootProperty>, t: Throwable) {
                    Toast.makeText(
                        context, "FAILURE" + t.message, Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<PokeRootProperty>,
                    response: Response<PokeRootProperty>) {

                    val itemsResponse = response.body()

                    //val user = itemsResponse?.results
                    numberList = itemsResponse?.results!!
                    //val length = response.body()?.results?.size
                    val recyclerView = poke_recycler_view
                    layoutManager = LinearLayoutManager(context)
                    recyclerView.layoutManager = layoutManager

                    val adapter = PokesAdapter(numberList, context)

                    recyclerView.adapter = adapter

                    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0) {
                            val visibleItemCount = layoutManager.childCount
                            val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                            val total = adapter.itemCount

                            if (!isLoading) {

                                if ((visibleItemCount + pastVisibleItem) >= total) {

                                    page+=20
                                    getPage(context, page, adapter)
                                    //////////////////////

                                }

                            }
//                }
                            super.onScrolled(recyclerView, dx, dy)
                        }
                    })

                }
            })

    }


    fun getPage(context: Context, page: Int, adapter: PokesAdapter) {
        isLoading = true
        //progressBar.visibility = View.VISIBLE

        Toast.makeText(context, "LOADING...", Toast.LENGTH_SHORT).show()

        PokeApi.retrofitService.getPokes(page)
            .enqueue(object : Callback<PokeRootProperty> {
                override fun onFailure(call: Call<PokeRootProperty>, t: Throwable) {
                    Toast.makeText(context, "FAILURE" + t.message, Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<PokeRootProperty>,
                    response: Response<PokeRootProperty>
                ) {
                    val length = response.body()?.results?.size

                    for (i in 0 until length!!) {
                        numberList.add((response.body()?.results!![i]))

                    }
                    adapter.notifyDataSetChanged()
                     isLoading = false

                }

            })
    }
    }
