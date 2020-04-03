package souza.home.com.pokedexapp.network.model.varieties

data class PokeRootVarieties(
    var evolution_chain: PokeEvolutionPath,
    var varieties: MutableList<PokeVarieties>
)