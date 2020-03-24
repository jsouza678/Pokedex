package souza.home.com.pokedexapp.network.model.evolution_chain

data class PokeEvolution(
    var evolves_to: List<PokeEvolution>?,
    var species: PokeSpecies?
)