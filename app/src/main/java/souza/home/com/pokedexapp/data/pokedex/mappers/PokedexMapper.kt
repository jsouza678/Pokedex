package souza.home.com.pokedexapp.data.pokedex.mappers

import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.data.pokedex.remote.model.pokemon.PokeRootProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.PokeVarietiesResponse
import souza.home.com.pokedexapp.data.remote.model.PokemonResponse
import souza.home.com.pokedexapp.data.pokedex.local.model.PokeVariationsEntity
import souza.home.com.pokedexapp.data.pokedex.local.model.PokemonEntity
import souza.home.com.pokedexapp.utils.TypeConverter
import souza.home.com.pokedexapp.utils.cropPokeUrl
import souza.home.com.pokedexapp.utils.optimizeDescription

class PokedexMapper {

    companion object {

        // Here the data fetched from API passes thru a transformation to store only poke id contained on pokeapi url.
        fun pokemonToDatabaseModel(pokeRootProperty: PokeRootProperty): Array<PokemonEntity>? {
            return pokeRootProperty.results?.map {
                PokemonEntity(
                    _id = cropPokeUrl(it._id),
                    name = it.name
                )
            }?.toTypedArray()
        }

        fun variationsAsDatabase(pokeVarietiesReponse : PokeVarietiesResponse) : PokeVariationsEntity {
            val pokeVarietiesAsString = TypeConverter.fromVarieties(pokeVarietiesReponse.varieties)
            val pokeDescriptionAsString = optimizeDescription(pokeVarietiesReponse.description)
            val pokeEvolutionChainAsString = TypeConverter.fromEvolutionChain(pokeVarietiesReponse.evolution_chain)
            val pokeColorAsString = TypeConverter.fromColor(pokeVarietiesReponse.color)

            return PokeVariationsEntity(
                _poke_variety_id = pokeVarietiesReponse._id,
                evolution_chain = pokeEvolutionChainAsString!!,
                varieties = pokeVarietiesAsString!!,
                color = pokeColorAsString!!,
                description = pokeDescriptionAsString
            )
        }

        fun variationsAsDomain(pokeVariationsEntity: PokeVariationsEntity): PokeVariety {

            val pokeEvolutionPathAsObject = TypeConverter.ToEvolutionPath(pokeVariationsEntity.evolution_chain)
            val pokeVarietiesAsList = TypeConverter.ToVarietiesList(pokeVariationsEntity.varieties)
            val pokeColorAsObject = TypeConverter.ToColor(pokeVariationsEntity.color)

            return PokeVariety(
                _id = pokeVariationsEntity._poke_variety_id,
                evolution_chain = pokeEvolutionPathAsObject!!,
                varieties = pokeVarietiesAsList!!,
                color = pokeColorAsObject!!,
                description = pokeVariationsEntity.description
            )
        }

        fun pokemonAsDomain(pokemonResponse : List<PokemonResponse>?): List<Poke>?{
            return pokemonResponse?.map{
                Poke(
                    url = it._id,
                    name = it.name
                )
            }
        }

    }
}