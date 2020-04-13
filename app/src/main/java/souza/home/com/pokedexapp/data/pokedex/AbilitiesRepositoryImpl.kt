package souza.home.com.pokedexapp.data.pokedex

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.mappers.PokedexMapper
import souza.home.com.pokedexapp.di.PokeApi
import souza.home.com.pokedexapp.domain.model.PokeAbility
import souza.home.com.pokedexapp.domain.repository.AbilitiesRepository

class AbilitiesRepositoryImpl(private val id: Int, context: Context) : AbilitiesRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    override val abilities: LiveData<PokeAbility>?
        get() = DB_INSTANCE.abilitiesDao.getAbilityData(id)?.let {
            Transformations.map(it){ abilityObject ->
                abilityObject?.let { it1 -> PokedexMapper.abilitiesAsDomain(it1) }
            }
        }

    override suspend fun refreshAbilities(id: Int) {
        withContext(Dispatchers.IO){
            try{
                val pokeAbility = PokeApi.retrofitService.getAbilityData(id).await()
                DB_INSTANCE.abilitiesDao.insertAll(PokedexMapper.abilitiesToDatabaseModel(pokeAbility))
            }catch(e: Exception){
                Log.i("Error" , "Message From Api on Abilities" + e.message)
            }
        }
    }
}