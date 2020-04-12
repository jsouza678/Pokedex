package souza.home.com.pokedexapp.domain.repository

import androidx.lifecycle.LiveData
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AllAbilitiesResponse
import souza.home.com.pokedexapp.domain.model.PokeAbility
import souza.home.com.pokedexapp.domain.model.PokeProperty

interface AbilitiesRepository {

    val abilities : LiveData<PokeAbility>?

    suspend fun refreshAbilities(id: Int)

}