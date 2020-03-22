package souza.home.com.pokedexapp.network.stats

import souza.home.com.pokedexapp.network.stats.PokeNestedStat

data class PokeStats(

    var base_stat: String = "",
    var effort: String = "",
    var stat: PokeNestedStat
)