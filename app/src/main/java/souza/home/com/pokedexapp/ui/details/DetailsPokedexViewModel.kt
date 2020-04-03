package souza.home.com.pokedexapp.ui.details

import android.R
import android.app.Application
import android.content.Context
import android.view.View
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.network.model.stats.PokeStats
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty
import souza.home.com.pokedexapp.network.model.varieties.PokeRootVarieties
import souza.home.com.pokedexapp.network.model.varieties.PokeVarieties
import souza.home.com.pokedexapp.ui.home.HomePokedexStatus

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class DetailsPokedexViewModel(pokemon: String, app: Application): AndroidViewModel(app) {



    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _stats = MutableLiveData<PokemonProperty>()

    val stats : LiveData<PokemonProperty>
        get() = _stats

    private var _varieties = MutableLiveData<MutableList<PokeVarieties>>()

    val varieties : LiveData<MutableList<PokeVarieties>>
        get() = _varieties

    private var _chain = MutableLiveData<MutableList<PokeEvolution>>()

    val chain : LiveData<MutableList<PokeEvolution>>
        get() = _chain

    init{
        getStats(pokemon)
        getChainEvolution(pokemon)
        getVarieties(pokemon)
    }

    fun getStats(pokemon: String){

        _status.value = DetailsPokedexStatus.LOADING

        PokeApi.retrofitService.getPokeStats(pokemon).enqueue(object: Callback<PokemonProperty> {
        override fun onFailure(call: Call<PokemonProperty>, t: Throwable) {
            _status.value = DetailsPokedexStatus.ERROR
        }

        override fun onResponse(call: Call<PokemonProperty>, response: Response<PokemonProperty>) {
            val item = response.body()

            _stats.value = item
            _status.value = DetailsPokedexStatus.DONE


        }

    })



}

fun getChainEvolution(pokemon: String){
    _status.value = DetailsPokedexStatus.LOADING
    PokeApi.retrofitService.getEvolutionChain(pokemon).enqueue(object:
        Callback<PokeEvolutionChain> {
        override fun onFailure(call: Call<PokeEvolutionChain>, t: Throwable) {
            _status.value = DetailsPokedexStatus.ERROR
        }

        override fun onResponse(call: Call<PokeEvolutionChain>, response: Response<PokeEvolutionChain>) {
            //Toast.makeText(context, "Success 2", Toast.LENGTH_LONG).show()
            val item = response.body()
            val evolutionArray : List<PokeEvolution>

            evolutionArray = ArrayList()
            evolutionArray.clear()


            if(item?.chain?.species?.name != null){  //// 1 CHAIN

                evolutionArray.add(item.chain)

                try{
                    evolutionArray.add(item.chain.evolves_to!![0])
                    try {
                        evolutionArray.add(item.chain.evolves_to!![0].evolves_to!![0])

                    }catch (e: Exception){

                    }
                }
                catch (e: Exception) {

                }

            }
            _chain.value = evolutionArray

            _status.value = DetailsPokedexStatus.DONE
        }

    })

}


fun getVarieties(pokemon: String) : MutableList<PokeVarieties>?{

    _status.value = DetailsPokedexStatus.LOADING

    PokeApi.retrofitService.getVariations(pokemon).enqueue(object: Callback<PokeRootVarieties> {
        override fun onFailure(call: Call<PokeRootVarieties>, t: Throwable) {
            _status.value = DetailsPokedexStatus.ERROR
        }

        override fun onResponse(call: Call<PokeRootVarieties>, response: Response<PokeRootVarieties>) {
            val varietiesArray: ArrayList<String> = ArrayList()
            val items = response.body()
            val length = response.body()?.varieties?.size

/*
            for(i in 0 until length!!) {
                try {
                    varietiesArray.add(response.body()?.varieties!![i].pokemon.name.capitalize())
                } catch (e: Exception) {
                    // varietiesArray.add("No varieties")
                }
            }*/

            _varieties.value = items?.varieties
/*








*/
            _status.value = DetailsPokedexStatus.DONE

        }
    }
    )
        return varieties.value
    }
}