package souza.home.com.pokedexapp.data.pokedex.remote.model.stats

import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.PokeAbilities
import souza.home.com.pokedexapp.data.pokedex.remote.model.types.PokeTypes

data class PokemonProperty(
    var id : Int = 0, // change to string
    var abilities : MutableList<PokeAbilities>,
    var name: String = "",
    var height: Int = 0,
    var sprites: PokeSprites,
    var stats: List<PokeStats>,
    var types: MutableList<PokeTypes>,
    var weight: Int = 0
)