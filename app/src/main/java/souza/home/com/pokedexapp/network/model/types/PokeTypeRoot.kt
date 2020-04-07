package souza.home.com.pokedexapp.network.model.types

import souza.home.com.pokedexapp.network.model.main_model.Pokemon

data class PokeTypeRoot(

    var pokemon: MutableList<PokemonNested>

)