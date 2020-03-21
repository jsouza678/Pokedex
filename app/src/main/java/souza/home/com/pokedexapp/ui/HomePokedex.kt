package souza.home.com.pokedexapp.ui


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home_pokedex.*
import retrofit2.Call
import retrofit2.Response

import souza.home.com.pokedexapp.databinding.FragmentHomePokedexBinding
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.PokeProperty
import souza.home.com.pokedexapp.network.PokeRootProperty


/**
 * A simple [Fragment] subclass.
 */
class HomePokedex : Fragment() {

    var visibleItemCount: Int = 0
    var limit = 10
    var loading: Boolean = false

    private lateinit var scrollListener: RecyclerView.OnScrollListener

    private lateinit var layoutManager: LinearLayoutManager

    private val lastVisibleItemPosition: Int
        get() = layoutManager.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomePokedexBinding.inflate(inflater)

        var layoutManager : RecyclerView.LayoutManager



        PokeApi.retrofitService.getPokes(20).enqueue(object : retrofit2.Callback<PokeRootProperty>{
            override fun onFailure(call: Call<PokeRootProperty>, t: Throwable) {
                Toast.makeText(
                    binding.root.context, "FAILURE" + t.message, Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<PokeRootProperty>, response: Response<PokeRootProperty>) {

                Toast.makeText(
                    binding.root.context, "carregando, tu que fez merda msm", Toast.LENGTH_SHORT
                ).show()

                val itemsResponse = response.body()


                val user = itemsResponse?.results

                //
                val recyclerView = poke_recycler_view
                layoutManager = LinearLayoutManager(binding.root.context)
                recyclerView.layoutManager = layoutManager
                val adapter = PokesAdapter(user, binding.root.context)
                recyclerView.adapter = adapter

                   // setRecyclerViewScrollListener()


                Toast.makeText(
                    binding.root.context, "carregando, tu que fez merda msm" + user?.get(1)?.name, Toast.LENGTH_SHORT
                ).show()

                //recyclerView!!.addItemDecoration(DividerItemDecoration(binding.root.context, GridLayoutManager.VERTICAL))

            }
        })

        return binding.root
    }



}

