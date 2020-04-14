package souza.home.com.pokedexapp.data.pokedex.remote.model.variety

import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

data class FlavorDescription(
    var flavor_text: String = EMPTY_STRING,
    var language: Language
)
