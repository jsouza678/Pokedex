package souza.home.com.pokedexapp.data.pokedex.remote.model.evolution_chain

data class PokeEvolution(
    var evolves_to: List<PokeEvolution>?,
    var species: PokeSpecies?
)