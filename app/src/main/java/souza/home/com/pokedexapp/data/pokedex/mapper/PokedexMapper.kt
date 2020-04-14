package souza.home.com.pokedexapp.data.pokedex.mapper

import souza.home.com.pokedexapp.data.pokedex.local.entities.EvolutionEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PropertyEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.VarietyEntity
import souza.home.com.pokedexapp.data.pokedex.remote.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokeRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokemonResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PropertyResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.VarietiesResponse
import souza.home.com.pokedexapp.domain.model.Poke
import souza.home.com.pokedexapp.domain.model.PokeEvolutionChain
import souza.home.com.pokedexapp.domain.model.PokeProperty
import souza.home.com.pokedexapp.domain.model.PokeVariety
import souza.home.com.pokedexapp.utils.TypeConverter
import souza.home.com.pokedexapp.utils.cropPokeUrl
import souza.home.com.pokedexapp.utils.optimizeChain
import souza.home.com.pokedexapp.utils.optimizeDescription

class PokedexMapper {

    companion object {

        fun pokemonToDatabaseModel(pokeRootProperty: PokeRootResponse): Array<PokemonEntity>? {
            return pokeRootProperty.results?.map {
                PokemonEntity(
                    _id = Integer.parseInt(cropPokeUrl(it._id)),
                    name = it.name) }?.toTypedArray()
        }

        fun evolutionChainToDatabaseModel(evolutionChainResponse: EvolutionChainResponse): EvolutionEntity {
            val pureEvolutionChain = optimizeChain(evolutionChainResponse)
            val evolutionAsString = TypeConverter.fromEvolution(pureEvolutionChain)

            return EvolutionEntity(
                _id = evolutionChainResponse._id,
                evolution = evolutionAsString!!
            )
        }

        fun variationsAsDatabase(pokeVarietiesReponse: VarietiesResponse): VarietyEntity {
            val pokeVarietiesAsString = TypeConverter.fromVarieties(pokeVarietiesReponse.varieties)
            val pokeDescriptionAsString = optimizeDescription(pokeVarietiesReponse.description)
            val pokeEvolutionChainAsString = TypeConverter.fromEvolutionPath(pokeVarietiesReponse.evolution_chain)
            val pokeColorAsString = TypeConverter.fromColor(pokeVarietiesReponse.color)

            return VarietyEntity(
                _poke_variety_id = Integer.parseInt(pokeVarietiesReponse._id),
                evolution_chain = pokeEvolutionChainAsString!!,
                varieties = pokeVarietiesAsString!!,
                color = pokeColorAsString!!,
                description = pokeDescriptionAsString)
        }

        fun propertiesAsDatabase(propertyResponse: PropertyResponse): PropertyEntity {
            val abilitiesAsString = TypeConverter.fromAbilities(propertyResponse.abilities)
            val spritesAsString = TypeConverter.fromSprites(propertyResponse.sprites)
            val statsAsString = TypeConverter.fromStats(propertyResponse.stats)
            val typesAsString = TypeConverter.fromTypes(propertyResponse.types)

            return PropertyEntity(
                id = propertyResponse.id,
                abilities = abilitiesAsString,
                name = propertyResponse.name,
                height = propertyResponse.height,
                sprites = spritesAsString,
                stats = statsAsString,
                types = typesAsString,
                weight = propertyResponse.weight
            )
        }

        fun variationsAsDomain(pokeVariationsEntity: VarietyEntity): PokeVariety {
            val pokeEvolutionPathAsObject = TypeConverter.toEvolutionPath(pokeVariationsEntity.evolution_chain)
            val pokeVarietiesAsList = TypeConverter.toVarietiesList(pokeVariationsEntity.varieties)
            val pokeColorAsObject = TypeConverter.toColor(pokeVariationsEntity.color)

            return PokeVariety(
                _id = pokeVariationsEntity._poke_variety_id,
                evolution_chain = pokeEvolutionPathAsObject!!,
                varieties = pokeVarietiesAsList!!,
                color = pokeColorAsObject!!,
                description = pokeVariationsEntity.description)
        }

        fun propertyAsDomain(propertyEntity: PropertyEntity): PokeProperty {
            val abilitiesAsList = TypeConverter.toAbilitiesList(propertyEntity.abilities)
            val spritesAsObject = TypeConverter.toSprites(propertyEntity.sprites)
            val statsAsList = TypeConverter.toStatsList(propertyEntity.stats)
            val typesAsList = TypeConverter.toTypesList(propertyEntity.types)

            return PokeProperty(
                id = propertyEntity.id,
                abilities = abilitiesAsList,
                name = propertyEntity.name,
                height = propertyEntity.height,
                sprites = spritesAsObject,
                stats = statsAsList,
                types = typesAsList,
                weight = propertyEntity.weight)
        }

        fun pokemonAsDomain(pokemonResponse: List<PokemonResponse>?): List<Poke>? {
            return pokemonResponse?.map {
                Poke(
                    _id = Integer.parseInt(it._id),
                    name = it.name) }
        }

        fun evolutionAsDomain(evolutionEntity: EvolutionEntity): PokeEvolutionChain {
            val chainAsEvolution = TypeConverter.toEvolution(evolutionEntity.evolution)

            return PokeEvolutionChain(
                _id = evolutionEntity._id,
                evolution = chainAsEvolution
            )
        }
    }
}