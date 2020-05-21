package souza.home.com.pokedexapp.data.pokedex.mapper

import souza.home.com.pokedexapp.data.pokedex.local.entities.EvolutionEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PropertyEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.VarietyEntity
import souza.home.com.pokedexapp.data.pokedex.remote.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokeRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokemonResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PropertyRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.VarietiesRootResponse
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.domain.model.Pokemon
import souza.home.com.pokedexapp.utils.TypeConverter
import souza.home.com.pokedexapp.utils.cropPokeUrl
import souza.home.com.pokedexapp.utils.optimizeChain
import souza.home.com.pokedexapp.utils.optimizeDescription

class PokedexMapper {

    companion object {

        fun pokemonResponseAsDatabaseModel(pokeRootProperty: PokeRootResponse): Array<PokemonEntity>? {
            return pokeRootProperty.results?.map {
                PokemonEntity(
                    _id = Integer.parseInt(cropPokeUrl(it._id)),
                    name = it.name) }?.toTypedArray()
        }

        fun evolutionChainResponseToDatabaseModel(evolutionChainResponse: EvolutionChainResponse): EvolutionEntity {
            val optimizedEvolutionChain = optimizeChain(evolutionChainResponse)
            val evolutionAsString = TypeConverter.fromEvolution(optimizedEvolutionChain)

            return EvolutionEntity(
                _id = evolutionChainResponse._id,
                evolution = evolutionAsString
            )
        }

        fun variationsResponseAsDatabaseModel(pokeVarietiesRootResponse: VarietiesRootResponse): VarietyEntity {
            val pokeVarietiesAsString = TypeConverter.fromVarieties(pokeVarietiesRootResponse.varieties)
            val pokeDescriptionAsString = optimizeDescription(pokeVarietiesRootResponse.description)
            val pokeEvolutionChainAsString = TypeConverter.fromEvolutionPath(pokeVarietiesRootResponse.evolutionChain)
            val pokeColorAsString = TypeConverter.fromColor(pokeVarietiesRootResponse.color)

            return VarietyEntity(
                _poke_variety_id = Integer.parseInt(pokeVarietiesRootResponse._id),
                evolution_chain = pokeEvolutionChainAsString,
                varieties = pokeVarietiesAsString,
                color = pokeColorAsString,
                description = pokeDescriptionAsString)
        }

        fun propertiesResponseAsDatabaseModel(propertyRootResponse: PropertyRootResponse): PropertyEntity {
            val abilitiesAsString = TypeConverter.fromAbilities(propertyRootResponse.abilities)
            val spritesAsString = TypeConverter.fromSprites(propertyRootResponse.sprites)
            val statsAsString = TypeConverter.fromStats(propertyRootResponse.stats)
            val typesAsString = TypeConverter.fromTypes(propertyRootResponse.types)

            return PropertyEntity(
                _id = propertyRootResponse.id,
                abilities = abilitiesAsString,
                name = propertyRootResponse.name,
                height = propertyRootResponse.height,
                sprites = spritesAsString,
                stats = statsAsString,
                types = typesAsString,
                weight = propertyRootResponse.weight
            )
        }

        fun variationsEntityAsDomainModel(pokeVariationsEntity: VarietyEntity): PokeVariety {
            val pokeEvolutionPathAsObject = TypeConverter.toEvolutionPath(pokeVariationsEntity.evolution_chain)
            val pokeVarietiesAsList = TypeConverter.toVarietiesList(pokeVariationsEntity.varieties)
            val pokeColorAsObject = TypeConverter.toColor(pokeVariationsEntity.color)

            return PokeVariety(
                id = pokeVariationsEntity._poke_variety_id,
                evolutionChain = pokeEvolutionPathAsObject,
                varieties = pokeVarietiesAsList,
                color = pokeColorAsObject,
                description = pokeVariationsEntity.description)
        }

        fun propertyEntityAsDomainModel(propertyEntity: PropertyEntity): PokeProperty {
            val abilitiesAsList = TypeConverter.toAbilitiesList(propertyEntity.abilities)
            val spritesAsObject = TypeConverter.toSprites(propertyEntity.sprites)
            val statsAsList = TypeConverter.toStatsList(propertyEntity.stats)
            val typesAsList = TypeConverter.toTypesList(propertyEntity.types)

            return PokeProperty(
                id = propertyEntity._id,
                abilities = abilitiesAsList,
                name = propertyEntity.name,
                height = propertyEntity.height,
                sprites = spritesAsObject,
                stats = statsAsList,
                types = typesAsList,
                weight = propertyEntity.weight)
        }

        fun pokemonEntityAsDomainModel(pokemonResponse: List<PokemonResponse>?): List<Pokemon>? {
            return pokemonResponse?.map {
                Pokemon(
                    id = Integer.parseInt(it._id),
                    name = it.name) }
        }

        fun evolutionEntityAsDomainModel(evolutionEntity: EvolutionEntity): PokeEvolutionChain {
            val chainAsEvolution = TypeConverter.toEvolution(evolutionEntity.evolution)

            return PokeEvolutionChain(
                id = evolutionEntity._id,
                evolution = chainAsEvolution
            )
        }
    }
}
