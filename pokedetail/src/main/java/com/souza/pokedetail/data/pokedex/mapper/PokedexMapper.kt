package com.souza.pokedetail.data.pokedex.mapper

import com.souza.pokedetail.data.pokedex.local.entities.AbilityEntity
import com.souza.pokedetail.data.pokedex.local.entities.EvolutionEntity
import com.souza.pokedetail.data.pokedex.local.entities.PropertyEntity
import com.souza.pokedetail.data.pokedex.local.entities.TypeEntity
import com.souza.pokedetail.data.pokedex.local.entities.VarietyEntity
import com.souza.pokedetail.data.pokedex.remote.response.AbilityRootResponse
import com.souza.pokedetail.data.pokedex.remote.response.EvolutionChainResponse
import com.souza.pokedetail.data.pokedex.remote.response.PropertyRootResponse
import com.souza.pokedetail.data.pokedex.remote.response.TypesRootResponse
import com.souza.pokedetail.data.pokedex.remote.response.VarietiesRootResponse
import com.souza.pokedetail.domain.model.PokeAbility
import com.souza.pokedetail.domain.model.PokeEvolutionChain
import com.souza.pokedetail.domain.model.PokeProperty
import com.souza.pokedetail.domain.model.PokeType
import com.souza.pokedetail.domain.model.PokeVariety
import com.souza.pokedetail.utils.TypeConverter
import com.souza.pokedetail.utils.optimizeChain
import com.souza.pokedetail.utils.optimizeDescription

class PokedexMapper {

    companion object {

        fun typeResponseAsDatabaseModel(typesRootResponse: TypesRootResponse): TypeEntity {
            val typeResponseAsString = TypeConverter.fromTypesResponse(typesRootResponse.pokemon)

            return TypeEntity(
                    _id = typesRootResponse.id,
                    pokemon = typeResponseAsString)
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

        fun evolutionEntityAsDomainModel(evolutionEntity: EvolutionEntity): PokeEvolutionChain {
            val chainAsEvolutionList = TypeConverter.toEvolution(evolutionEntity.evolution)

            return PokeEvolutionChain(
                id = evolutionEntity._id,
                evolution = chainAsEvolutionList
            )
        }

        fun abilityAsDomainModel(abilityEntity: AbilityEntity): PokeAbility {
            return PokeAbility(
                id = abilityEntity._id,
                description = abilityEntity.effect)
        }

        fun typeAsDomainModel(typeEntity: TypeEntity): PokeType {
            val typeAsList = TypeConverter.toTypesResponseList(typeEntity.pokemon)

            return PokeType(
                id = typeEntity._id,
                pokesInTypes = typeAsList
            )
        }
    }
}
