package souza.home.com.pokedexapp.domain.model

import souza.home.com.pokedexapp.data.pokedex.remote.model.response.NestedType

data class PokeType(
    var _id: Int,
    var types: MutableList<NestedType>?
)