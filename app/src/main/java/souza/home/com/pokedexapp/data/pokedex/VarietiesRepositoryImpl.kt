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
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.repository.VarietiesRepository

enum class VarietiesPokedexStatus { LOADING, ERROR, DONE, EMPTY }

class VarietiesRepositoryImpl(private val id: Int, context: Context) : VarietiesRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    private val _internet = MutableLiveData<VarietiesPokedexStatus>()

    val internet: LiveData<VarietiesPokedexStatus>
        get() = _internet

    override val varieties: LiveData<PokeVariety?>? = Transformations.map(DB_INSTANCE.varietiesDao.getVariety(id)) {
        it?.let { it1 -> PokedexMapper.variationsAsDomain(it1) }
    }

    override suspend fun refreshVarieties(id: Int) {
        withContext(Dispatchers.IO) {
            _internet.postValue(VarietiesPokedexStatus.LOADING)
            try {
                val pokeVariations = PokeApi.retrofitService.getVariations(id).await()
                DB_INSTANCE.varietiesDao.insertAll(PokedexMapper.variationsAsDatabase(pokeVariations))
                if (pokeVariations._id.isNullOrEmpty()) {
                    _internet.postValue(VarietiesPokedexStatus.EMPTY)
                } else {
                    _internet.postValue(VarietiesPokedexStatus.DONE)
                }
            } catch (e: Exception) {
                _internet.postValue(VarietiesPokedexStatus.ERROR)
                Log.i("Error", "Message From Api on Varieties" + e.message)
            }
        }
    }
}
