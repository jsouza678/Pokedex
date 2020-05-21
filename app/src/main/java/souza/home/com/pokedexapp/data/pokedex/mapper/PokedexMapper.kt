package souza.home.com.pokedexapp.data.pokedex.mapper

import souza.home.com.pokedexapp.data.pokedex.local.entities.AbilityEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.EvolutionEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PokemonEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.PropertyEntity
import souza.home.com.pokedexapp.data.pokedex.local.entities.VarietyEntity
import souza.home.com.pokedexapp.data.pokedex.remote.response.AbilityRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.EvolutionChainResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PokeRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.PropertyRootResponse
import souza.home.com.pokedexapp.data.pokedex.remote.response.VarietiesRootResponse
import souza.home.com.pokedexapp.domain.model.PokeAbility
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
                    _id = Integer.parseInt(cropPokeUrl(it.id)),
                    name = it.name) }?.toTypedArray()
        }

        fun evolutionChainResponseToDatabaseModel(evolutionChainResponse: EvolutionChainResponse): EvolutionEntity {
            val optimizedEvolutionChain = optimizeChain(evolutionChainResponse)
            val evolutionAsString = TypeConverter.fromEvolution(optimizedEvolutionChain)

            return EvolutionEntity(
                _id = evolutionChainResponse.id,
                evolution = evolutionAsString
            )
        }

        fun variationsResponseAsDatabaseModel(pokeVarietiesRootResponse: VarietiesRootResponse): VarietyEntity {
            val pokeVarietiesAsString = TypeConverter.fromVarieties(pokeVarietiesRootResponse.varieties)
            val pokeDescriptionAsString = optimizeDescription(pokeVarietiesRootResponse.description)
            val pokeEvolutionChainAsString = TypeConverter.fromEvolutionPath(pokeVarietiesRootResponse.evolutionChain)
            val pokeColorAsString = TypeConverter.fromColor(pokeVarietiesRootResponse.color)

            return VarietyEntity(
                _id = Integer.parseInt(pokeVarietiesRootResponse.id),
                evolution_chain = pokeEvolutionChainAsString,
                varieties = pokeVarietiesAsString,
                color = pokeColorAsString,
                description = pokeDescriptionAsString)
        }

        fun propertyResponseAsDatabaseModel(propertyRootResponse: PropertyRootResponse): PropertyEntity {
            val abilitiesAsString = TypeConverter.fromAbility(propertyRootResponse.abilities)
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

        fun abilityResponseAsDatabaseModel(abilityRootResponse: AbilityRootResponse): AbilityEntity {
            val abilityDescriptionAsString = abilityRootResponse.effect?.get(0)?.effect

            return AbilityEntity(
                _id = abilityRootResponse.id,
                effect = abilityDescriptionAsString
            )
        }

        fun varietyEntityAsDomainModel(varietyEntity: VarietyEntity): PokeVariety {
            val pokeEvolutionPathAsObject = TypeConverter.toEvolutionPath(varietyEntity.evolution_chain)
            val pokeVarietiesAsList = TypeConverter.toVarietiesList(varietyEntity.varieties)
            val pokeColorAsObject = TypeConverter.toColor(varietyEntity.color)

            return PokeVariety(
                id = varietyEntity._id,
                evolutionChain = pokeEvolutionPathAsObject,
                varieties = pokeVarietiesAsList,
                color = pokeColorAsObject,
                description = varietyEntity.description)
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

        fun pokemonEntityAsDomainModel(pokemonEntity: List<PokemonEntity>?): List<Pokemon>? {
            return pokemonEntity?.map {
                Pokemon(
                    id = it._id,
                    name = it.name) }
        }

        fun evolutionEntityAsDomainModel(evolutionEntity: EvolutionEntity): PokeEvolutionChain {
            val chainAsEvolution = TypeConverter.toEvolution(evolutionEntity.evolution)

            return PokeEvolutionChain(
                id = evolutionEntity._id,
                evolution = chainAsEvolution
            )
        }

        fun abilityAsDomainModel(abilityEntity: AbilityEntity): PokeAbility {
            return PokeAbility(
                id = abilityEntity._id,
                description = abilityEntity.effect) }
    }
}
