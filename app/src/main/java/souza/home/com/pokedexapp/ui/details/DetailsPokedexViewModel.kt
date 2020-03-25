package souza.home.com.pokedexapp.ui.details

import android.R
import android.content.Context
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import souza.home.com.pokedexapp.network.model.varieties.PokeRootVarieties

class DetailsPokedexViewModel : ViewModel() {


fun getStats(
    pokemon: String,
    context: Context,
    textViewName: TextView,
    tvHp: TextView,
    tvAttack: TextView,
    tvDeffense: TextView,
    tvSpecialAttack: TextView,
    tvSpecialDefense: TextView,
    tvSpeed: TextView,
    lvTypes: ListView,
    lvAbilities: ListView
){
    PokeApi.retrofitService.getPokeStats(pokemon).enqueue(object: Callback<PokemonProperty> {
        override fun onFailure(call: Call<PokemonProperty>, t: Throwable) {
            Toast.makeText(context, "Failure on getting stats" + t.message, Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call<PokemonProperty>, response: Response<PokemonProperty>) {
            //  Toast.makeText(context, "Success 1", Toast.LENGTH_SHORT).show()
            val item = response.body()

            val adapterTypes = ArrayAdapter(context, R.layout.simple_list_item_1, item?.types!!)
            val adapterAbilities = ArrayAdapter(context, R.layout.simple_list_item_1, item?.abilities!!)

            lvTypes.adapter = adapterTypes
            lvAbilities.adapter = adapterAbilities
            textViewName.text = item?.name

            Toast.makeText(context, item?.abilities!![0].ability.name, Toast.LENGTH_LONG).show()

            //Toast.makeText(context, item.name,Toast.LENGTH_SHORT).show()
            tvHp.text = item?.stats!![5].base_stat
            tvAttack.text = item.stats[4].base_stat
            tvDeffense.text = item.stats[3].base_stat
            tvSpecialAttack.text = item.stats[2].base_stat
            tvSpecialDefense.text = item.stats[1].base_stat
            tvSpeed.text = item.stats[0].base_stat
            //lv_types.adapter = adapterTypes




        }

    })



}

fun getChainEvolution(
    pokemon: String,
    context: Context,
    evolutionArray: ArrayList<String>,
    listViewChain: ListView
){
    PokeApi.retrofitService.getEvolutionChain(pokemon).enqueue(object:
        Callback<PokeEvolutionChain> {
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

                    }catch (e: Exception){

                    }
                }
                catch (e: Exception) {

                }

            } else{
                //evolutionArray.clear()
                evolutionArray.add("This Poke does not evolutes") //////// HARDCODED
            }

            val adapterChain = ArrayAdapter(context, R.layout.simple_list_item_1, evolutionArray)


            listViewChain.adapter = adapterChain
        }

    })

}

fun getVarieties(
    pokemon: String,
    context: Context,
    varietiesArray: ArrayList<String>,
    spVariations: Spinner,
    evolutionArray: ArrayList<String>,
    listViewChain: ListView,
    textViewName: TextView,
    tvHp: TextView,
    tvAttack: TextView,
    tvDeffense: TextView,
    tvSpecialAttack: TextView,
    tvSpecialDefense: TextView,
    tvSpeed: TextView,
    lvTypes: ListView,
    lvAbilities: ListView
){

    PokeApi.retrofitService.getVariations(pokemon).enqueue(object: Callback<PokeRootVarieties> {
        override fun onFailure(call: Call<PokeRootVarieties>, t: Throwable) {
            Toast.makeText(context, "FAILURE on getting varieties " + t.message, Toast.LENGTH_SHORT).show()
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

            val spinnerAdapter = ArrayAdapter(context, R.layout.simple_spinner_item, varietiesArray)

            var urlChain = items?.evolution_chain?.url

            // calling chain evoltuion at selection
            var pokePath = urlChain?.substringAfterLast("n/")
            //Toast.makeText(context, pokePath, Toast.LENGTH_SHORT).show()

            getChainEvolution(pokePath!!, context, evolutionArray, listViewChain)

            spVariations.adapter = spinnerAdapter

            spVariations.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                    // Display the selected item text on text view


                    urlChain = items!!.varieties[position].pokemon.url
                    pokePath = urlChain?.substringAfterLast("n/")
                    //Toast.makeText(context, pokePath, Toast.LENGTH_SHORT).show()

                    getStats(pokePath!!, context, textViewName, tvHp, tvAttack, tvDeffense, tvSpecialAttack, tvSpecialDefense, tvSpeed, lvTypes, lvAbilities)
                    getChainEvolution(pokePath!!, context, evolutionArray, listViewChain)

                    //Toast.makeText(context,"selected", Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>){
                    getChainEvolution(pokePath!!, context, evolutionArray, listViewChain)
                }
            }
        }
    })
}
}