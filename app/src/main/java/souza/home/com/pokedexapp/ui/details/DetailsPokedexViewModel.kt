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


    private var _varieties = MutableLiveData<PokeRootVarieties>()

    val varieties : LiveData<PokeRootVarieties>
        get() = _varieties

    private var _chain = MutableLiveData<MutableList<PokeEvolution>>()

    val chain : LiveData<MutableList<PokeEvolution>>
        get() = _chain

    init{

    }

}
