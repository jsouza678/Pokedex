package souza.home.com.pokedexapp.network.model.stats

data class PokeStats(

    var base_stat: String = "",
    var effort: String = "",
    var stat: PokeNestedStat
)