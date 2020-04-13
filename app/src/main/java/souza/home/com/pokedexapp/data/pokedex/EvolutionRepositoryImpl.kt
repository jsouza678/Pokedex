package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.di.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.repository.EvolutionRepository

enum class EvolutionPokedexStatus{ LOADING, ERROR, DONE, EMPTY}

class EvolutionRepositoryImpl(id: Int, context: Context) : EvolutionRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    private val _internet = MutableLiveData<EvolutionPokedexStatus>()

    val internet : LiveData<EvolutionPokedexStatus>
        get() = _internet

    override val evolution: LiveData<PokeEvolutionChain>? =
        DB_INSTANCE.evolutionChainDao.getEvolutionChain(id)?.let {
            Transformations.map(it){ evolutionObject ->
                evolutionObject?.let { evolutionItem -> PokedexMapper.evolutionAsDomain(evolutionItem) }
            }
        }

    override suspend fun refreshEvolutionChain(id: Int) {
        withContext(Dispatchers.IO){
            _internet.postValue(EvolutionPokedexStatus.LOADING)
            try{
                val pokeEvolution = PokeApi.retrofitService.getEvolutionChain(id).await()
                DB_INSTANCE.evolutionChainDao.insertAll(PokedexMapper.evolutionChainToDatabaseModel(pokeEvolution))

                if(pokeEvolution.chain.species?.name.isNullOrBlank()){
                    _internet.postValue(EvolutionPokedexStatus.EMPTY)
                }else{
                    _internet.postValue(EvolutionPokedexStatus.DONE)
                }
            }catch(e: Exception){
                _internet.postValue(EvolutionPokedexStatus.ERROR)
                Log.i("Error" , "Message From Api on Evolution" + e.message)
            }
        }
    }
}