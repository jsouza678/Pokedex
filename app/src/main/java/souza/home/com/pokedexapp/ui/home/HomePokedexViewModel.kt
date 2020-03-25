package souza.home.com.pokedexapp.ui.home

import android.app.PendingIntent.getActivity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.PokeRootProperty
import souza.home.com.pokedexapp.network.model.main_model.Pokemon
import souza.home.com.pokedexapp.ui.details.DetailsPokedexFragment
import androidx.appcompat.app.AppCompatActivity
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.fragment.app.Fragment


class HomePokedexViewModel : ViewModel(){

    lateinit var layoutManager: LinearLayoutManager
    var page = 0
    var isLoading = false

    private val _poke = MutableLiveData<MutableList<Pokemon>>()

    fun loadFirstPage(recyclerView: RecyclerView, context: Context){

        PokeApi.retrofitService.getPokes(page).enqueue(object : Callback<PokeRootProperty> { override fun onFailure(call: Call<PokeRootProperty>, t: Throwable) {
                    Toast.makeText(context, "FAILURE" + t.message, Toast.LENGTH_SHORT ).show()
                }
                override fun onResponse(call: Call<PokeRootProperty>, response: Response<PokeRootProperty>) {
                    Toast.makeText(context, "call success", Toast.LENGTH_SHORT).show()
                    val itemsResponse = response.body()

                    _poke.value  = itemsResponse?.results!!

                    layoutManager = LinearLayoutManager(context)
                    recyclerView.layoutManager = layoutManager

                    val adapter =
                        PokesAdapter(_poke.value, context)

                    recyclerView.adapter = adapter

                    adapter.onItemClick = {

                        val urlChain = it.url
                        val pokePath = urlChain?.substringAfterLast("n/")

                        val details = DetailsPokedexFragment(pokePath)

                        val manager = getFragmentManager(context)

                        manager.beginTransaction().replace(souza.home.com.pokedexapp.R.id.nav_host_fragment, details).addToBackStack(null).commit()

                    }


                    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
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
                    //Toast.makeText(context, "call success", Toast.LENGTH_SHORT).show()

                    val length = response.body()?.results?.size

                    for (i in 0 until length!!) {
                        _poke.value?.add((response.body()?.results!![i]))

                    }

                    adapter.notifyDataSetChanged()
                    isLoading = false

                }

            })
    }

    fun getFragmentManager(context: Context): FragmentManager {
        return (context as AppCompatActivity).supportFragmentManager
    }

}