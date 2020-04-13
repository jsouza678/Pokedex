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
import souza.home.com.pokedexapp.domain.model.PokeType
import souza.home.com.pokedexapp.domain.repository.TypesRepository

class TypesRepositoryImpl(private val id: Int, context: Context) : TypesRepository {

    private val DB_INSTANCE = PokemonDatabase.getDatabase(context)

    override val types: LiveData<PokeType>?
        get() = DB_INSTANCE.typesDao.getTypeData(id)?.let {
            Transformations.map(it){ propertyObject ->
                propertyObject?.let { it1 -> PokedexMapper.typesAsDomain(it1) }
            }
        }

    override suspend fun refreshtypes(id: Int) {
        withContext(Dispatchers.IO){
            try{
                val pokeTypes = PokeApi.retrofitService.getTypeData(id).await()
                DB_INSTANCE.typesDao.insertAll(PokedexMapper.typesToDatabaseModel(pokeTypes))
            }catch(e: Exception){
                Log.i("Error" , "Message From Api on Types" + e.message)
            }
        }
    }
}