package souza.home.com.pokedexapp.data.pokedex.remote.model.response

import com.squareup.moshi.JsonClass
import souza.home.com.pokedexapp.utils.Constants.Companion.EMPTY_STRING

@JsonClass(generateAdapter = true)
data class AbilitiesDescription(
    var effect: String? = EMPTY_STRING
)