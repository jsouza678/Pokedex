package souza.home.com.pokedexapp.network.model.stats

data class PokemonProperty(
    var abilities : List<PokeAbilities>,
    var id : String? = "",
    var name: String? = "",
    var sprites: PokeSprites,
    var stats: List<PokeStats>,
    var types: List<PokeTypes>,
    var weight: String? = ""
)