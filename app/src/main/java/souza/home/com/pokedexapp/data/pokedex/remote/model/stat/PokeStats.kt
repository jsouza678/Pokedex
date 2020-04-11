package souza.home.com.pokedexapp.data.pokedex.remote.model.stat

import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

data class PokeStats(

    var base_stat: String = EMPTY_STRING,
    var effort: String = EMPTY_STRING,
    var stat: PokeNestedStat
)