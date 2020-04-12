package souza.home.com.pokedexapp.data.pokedex.remote.model.stat

import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

data class Stats(
    var base_stat: String?,
    var effort: String?,
    var stat: NestedStat?
)