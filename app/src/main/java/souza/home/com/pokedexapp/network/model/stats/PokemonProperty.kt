package souza.home.com.pokedexapp.network.model.stats

data class PokemonProperty(
    var abilities : MutableList<PokeAbilities>,
    var id : Int = 0,
    var name: String = "",
    var height: Int = 0,
    var sprites: PokeSprites,
    var stats: List<PokeStats>,
    var types: MutableList<PokeTypes>,
    var weight: Int = 0
)