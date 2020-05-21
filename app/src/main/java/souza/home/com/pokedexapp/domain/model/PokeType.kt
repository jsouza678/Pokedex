package souza.home.com.pokedexapp.domain.model

import souza.home.com.pokedexapp.data.pokedex.remote.response.TypeResponse

data class PokeType(
    val id: Int,
    val pokesInTypes: MutableList<TypeResponse>?
)
