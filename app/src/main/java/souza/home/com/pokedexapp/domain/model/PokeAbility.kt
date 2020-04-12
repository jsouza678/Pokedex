package souza.home.com.pokedexapp.domain.model

import souza.home.com.pokedexapp.data.pokedex.remote.model.response.AbilitiesDescription

data class PokeAbility(
    var _id: Int,
    var effect: MutableList<AbilitiesDescription>?
)