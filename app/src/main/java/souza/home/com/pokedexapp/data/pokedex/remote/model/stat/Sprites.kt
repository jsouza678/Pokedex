package souza.home.com.pokedexapp.data.pokedex.remote.model.stat

import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

data class Sprites(
    var back_default: String? = EMPTY_STRING,
    var back_female: String? = EMPTY_STRING,
    var back_shiny: String? = EMPTY_STRING,
    var back_shiny_female: String? = EMPTY_STRING,
    var front_default: String? = EMPTY_STRING,
    var front_female: String? = EMPTY_STRING,
    var front_shiny: String? = EMPTY_STRING,
    var front_shiny_female: String? = EMPTY_STRING
)