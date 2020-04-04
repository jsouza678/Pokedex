package souza.home.com.pokedexapp.ui.details.chain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import souza.home.com.pokedexapp.network.PokeApi
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolution
import souza.home.com.pokedexapp.network.model.evolution_chain.PokeEvolutionChain
import souza.home.com.pokedexapp.network.model.stats.PokemonProperty

enum class DetailsPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class PokeChainViewModel(pokemon: String, app: Application): AndroidViewModel(app) {

    private var _status = MutableLiveData<DetailsPokedexStatus>()

    val status : LiveData<DetailsPokedexStatus>
        get() = _status

    private var _chain = MutableLiveData<MutableList<PokeEvolution>>()

    val chain : LiveData<MutableList<PokeEvolution>>
        get() = _chain


    init {
        getChainEvolution(pokemon)
    }

    private fun getChainEvolution(pokemon: String){
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
}