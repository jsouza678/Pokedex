package souza.home.com.pokedexapp.ui.details


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_details_pokedex.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import souza.home.com.pokedexapp.databinding.FragmentDetailsPokedexBinding
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.stats.PokemonProperty
import souza.home.com.pokedexapp.network.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.network.varieties.PokeRootVarieties


/**
 * A simple [Fragment] subclass.
 */
class DetailsPokedexFragment : Fragment() {


    private lateinit var tvName : TextView
    private lateinit var lvStats : ListView
    private lateinit var lvTypes : ListView
    private lateinit var lvChain : ListView
    private lateinit var spVariations : Spinner
    private lateinit var evolutionArray: ArrayList<String>
    private lateinit var varietiesArray: ArrayList<String>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentDetailsPokedexBinding.inflate(inflater)

        val poke: String = "25"

        getStats(poke, binding.root.context)

        //getChainEvolution("10", binding.root.context)

        getVarieties(poke, binding.root.context)


        evolutionArray = ArrayList<String>()
        varietiesArray = ArrayList<String>()

        tvName = binding.tvDetailName



        lvStats = binding.lvStats
        lvTypes = binding.lvTypes
        lvChain = binding.lvChain
        spVariations = binding.spinnerVariations


        return binding.root
    }


    fun getStats(pokemon: String, context: Context){
        PokeApi.retrofitService.getPokeStats(pokemon).enqueue(object: Callback<PokemonProperty> {
            override fun onFailure(call: Call<PokemonProperty>, t: Throwable) {
                Toast.makeText(context, "Failure on getting stats" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PokemonProperty>, response: Response<PokemonProperty>) {
              //  Toast.makeText(context, "Success 1", Toast.LENGTH_SHORT).show()
                val item = response.body()



                tvName.text = item?.name


                val adapterStats = ArrayAdapter(context, android.R.layout.simple_list_item_1, item?.stats!!)
                //val adapterTypes = ArrayAdapter(context, android.R.layout.simple_list_item_1, item?.)


                lv_stats.adapter = adapterStats
                //lv_types.adapter = adapterTypes




            }

        })



    }

    private fun getChainEvolution(pokemon: String, context: Context){
        PokeApi.retrofitService.getEvolutionChain(pokemon).enqueue(object: Callback<PokeEvolutionChain> {
            override fun onFailure(call: Call<PokeEvolutionChain>, t: Throwable) {
                Toast.makeText(context, "Failure on getting Chain Evolution" + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PokeEvolutionChain>, response: Response<PokeEvolutionChain>) {
                //Toast.makeText(context, "Success 2", Toast.LENGTH_LONG).show()
                val item = response.body()
                evolutionArray.clear()
                if(item?.chain?.species?.name != null){  //// 1 CHAIN

                    evolutionArray.add(item.chain.species?.name!!)

                    try{
                        evolutionArray.add(item.chain.evolves_to!![0].species?.name!!)
                        try {
                            evolutionArray.add(item.chain.evolves_to!![0].evolves_to?.get(0)?.species?.name!!)
                            //Toast.makeText(context, "achei", Toast.LENGTH_LONG).show()
                        }catch (e: Exception){
                           // Toast.makeText(context, "n deu2 ", Toast.LENGTH_LONG).show()
                        }
                    }
                    catch (e: Exception) {
                        //Toast.makeText(context, "n deu1 ", Toast.LENGTH_LONG).show()
                    }

                } else{
                    //evolutionArray.clear()
                    evolutionArray.add("This Poke does not evolutes") //////// HARDCODED
                }

                val adapterChain = ArrayAdapter(context, android.R.layout.simple_list_item_1, evolutionArray)


                lv_chain.adapter = adapterChain
            }

        })

    }

    private fun getVarieties(pokemon: String, context: Context){

        PokeApi.retrofitService.getVariations(pokemon).enqueue(object: Callback<PokeRootVarieties> {
            override fun onFailure(call: Call<PokeRootVarieties>, t: Throwable) {
                Toast.makeText(context, "FAILURE o getting varieties " + t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PokeRootVarieties>, response: Response<PokeRootVarieties>) {
                //Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()
                val items = response.body()
                val length = response.body()?.varieties?.size
                //varietiesArray = response.body().varieties


                for(i in 0 until length!!) {
                    try {
                        varietiesArray.add(response.body()?.varieties!![i].pokemon.name)
                    } catch (e: Exception) {
                       // varietiesArray.add("No varieties")
                    }
                }

                val spinnerAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, varietiesArray)

                var urlChain = items?.evolution_chain?.url

                // calling chain evoltuion at selection
                var poke_path = urlChain?.substringAfterLast("n/")
                //Toast.makeText(context, poke_path, Toast.LENGTH_SHORT).show()

                getChainEvolution(poke_path!!, context)

                spVariations.adapter = spinnerAdapter

                spVariations.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                        // Display the selected item text on text view


                        urlChain = items!!.varieties[position].pokemon.url
                        poke_path = urlChain?.substringAfterLast("n/")
                        //Toast.makeText(context, poke_path, Toast.LENGTH_SHORT).show()

                        getStats(poke_path!!, context)
                       getChainEvolution(poke_path!!, context)

                        //Toast.makeText(context,"selected", Toast.LENGTH_SHORT).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>){
                        getChainEvolution(poke_path!!, context)
                    }
                }
            }
        })
    }
}
