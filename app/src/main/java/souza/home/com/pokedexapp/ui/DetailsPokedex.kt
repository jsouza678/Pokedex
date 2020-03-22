package souza.home.com.pokedexapp.ui


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_details_pokedex.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import souza.home.com.pokedexapp.databinding.FragmentDetailsPokedexBinding
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.stats.PokemonProperty
import souza.home.com.pokedexapp.network.evolution_chain.PokeEvolutionChain


/**
 * A simple [Fragment] subclass.
 */
class DetailsPokedex : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailsPokedexBinding.inflate(inflater)

        val poke: String = "1"

        getPoke(poke, binding.root.context)

        binding.tv1
        binding.tv2
        binding.tv3
        binding.lvTest

        getChainEvolution(poke, binding.root.context)

        return binding.root
    }


    fun getPoke(pokemon: String, context: Context){
        PokeApi.retrofitService.searchPokes(pokemon).enqueue(object: Callback<PokemonProperty> {
            override fun onFailure(call: Call<PokemonProperty>, t: Throwable) {
                Toast.makeText(context, "Failure 1" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PokemonProperty>, response: Response<PokemonProperty>) {
                Toast.makeText(context, "Success 1", Toast.LENGTH_SHORT).show()
                val item = response.body()


                tv_1.text = item?.name

                val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, item?.stats!!)
                lv_test.adapter = adapter
                tv_2.text = item.stats[1].stat.stat
                tv_3.text = item.stats[1].base_stat

            }

        })



    }

    fun getChainEvolution(pokemon: String, context: Context){
        PokeApi.retrofitService.getEvolutionChain(pokemon).enqueue(object: Callback<PokeEvolutionChain> {
            override fun onFailure(call: Call<PokeEvolutionChain>, t: Throwable) {
                Toast.makeText(context, "Failure 2" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PokeEvolutionChain>, response: Response<PokeEvolutionChain>) {
                Toast.makeText(context, "Success 2" + response.body()?.chain!!.evolves_to[0].evolves_to[0].species.name, Toast.LENGTH_LONG).show()
            }

        })

    }

}
