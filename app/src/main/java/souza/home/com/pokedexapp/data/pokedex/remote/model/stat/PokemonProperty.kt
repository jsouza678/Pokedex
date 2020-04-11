package souza.home.com.pokedexapp.data.pokedex.remote.model.stat

import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.PokeAbilities
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.PokeTypes
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

data class PokemonProperty(
    var id : Int = 0,
    var abilities : MutableList<PokeAbilities>,
    var name: String = EMPTY_STRING,
    var height: Int = 0,
    var sprites: PokeSprites,
    var stats: List<PokeStats>,
    var types: MutableList<PokeTypes>,
    var weight: Int = 0
)