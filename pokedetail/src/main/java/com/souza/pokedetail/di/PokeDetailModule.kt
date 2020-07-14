package com.souza.pokedetail.di

import androidx.room.Room
import com.souza.pokedetail.data.pokedex.AbilityRepositoryImpl
import com.souza.pokedetail.data.pokedex.EvolutionRepositoryImpl
import com.souza.pokedetail.data.pokedex.PropertyRepositoryImpl
import com.souza.pokedetail.data.pokedex.TypeRepositoryImpl
import com.souza.pokedetail.data.pokedex.VarietyRepositoryImpl
import com.souza.pokedetail.data.pokedex.local.AbilityDao
import com.souza.pokedetail.data.pokedex.local.DetailDatabase
import com.souza.pokedetail.data.pokedex.local.EvolutionChainDao
import com.souza.pokedetail.data.pokedex.local.PropertyDao
import com.souza.pokedetail.data.pokedex.local.TypeDao
import com.souza.pokedetail.data.pokedex.local.VarietiesDao
import com.souza.pokedetail.data.pokedex.remote.PokeDetailService
import com.souza.pokedetail.data.pokedex.remote.model.ability.AbilitiesRoot
import com.souza.pokedetail.data.pokedex.remote.model.type.TypeRoot
import com.souza.pokedetail.data.pokedex.remote.model.variety.Varieties
import com.souza.pokedetail.domain.repository.AbilityRepository
import com.souza.pokedetail.domain.repository.EvolutionRepository
import com.souza.pokedetail.domain.repository.PropertyRepository
import com.souza.pokedetail.domain.repository.TypeRepository
import com.souza.pokedetail.domain.repository.VarietyRepository
import com.souza.pokedetail.domain.usecase.FetchAbilityFromApi
import com.souza.pokedetail.domain.usecase.FetchEvolutionChainFromApi
import com.souza.pokedetail.domain.usecase.FetchPokesInTypesFromApi
import com.souza.pokedetail.domain.usecase.FetchPropertiesFromApi
import com.souza.pokedetail.domain.usecase.FetchVarietiesFromApi
import com.souza.pokedetail.domain.usecase.GetAbilityFromDatabase
import com.souza.pokedetail.domain.usecase.GetEvolutionChainFromDatabase
import com.souza.pokedetail.domain.usecase.GetPokesInTypesFromDatabase
import com.souza.pokedetail.domain.usecase.GetPropertiesFromDatabase
import com.souza.pokedetail.domain.usecase.GetVarietiesFromDatabase
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsActivityViewModel
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsGalleryAdapter
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsViewModel
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.about.AboutSpinnerAdapter
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.about.AboutViewModel
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain.EvolutionChainAdapter
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.evolutionchain.EvolutionChainViewModel
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.OthersAbilityAdapter
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.OthersTypeAdapter
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.others.OthersViewModel
import com.souza.pokedetail.presentation.pokedetails.pokeattributes.stats.StatsViewModel
import com.souza.shared_components.di.SHARED_RETROFIT
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val varietyDao = "VARIETY_DAO"
private const val propertyDao = "PROPERTY_DAO"
private const val abilityDao = "ABILITY_DAO"
private const val typeDao = "TYPE_DAO"
private const val evolutionDao = "EVOLUTION_CHAIN_DAO"
private const val detailDatabase = "DETAIL_DATABASE"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val pokeDetailModule = module {

    // ViewModels
    viewModel { (chainId: Int) ->
        EvolutionChainViewModel(
            chainId,
            get<FetchEvolutionChainFromApi>(),
            get<GetEvolutionChainFromDatabase>()
        )
    }

    viewModel { (pokeId: Int) ->
        AboutViewModel(
            pokeId,
            get<FetchVarietiesFromApi>(),
            get<GetVarietiesFromDatabase>()
        )
    }

    viewModel { (pokeId: Int) ->
        StatsViewModel(
            pokeId,
            get<FetchPropertiesFromApi>(),
            get<GetPropertiesFromDatabase>()
        )
    }

    viewModel { (pokeId: Int) ->
        OthersViewModel(
            pokeId,
            get<FetchPropertiesFromApi>(),
            get<GetPropertiesFromDatabase>(),
            get<FetchAbilityFromApi>(),
            get<GetAbilityFromDatabase>(),
            get<FetchPokesInTypesFromApi>(),
            get<GetPokesInTypesFromDatabase>()
        )
    }

    viewModel { (pokeId: Int) ->
        PokeDetailsViewModel(
            pokeId,
            get<FetchVarietiesFromApi>(),
            get<GetVarietiesFromDatabase>(),
            get<FetchPropertiesFromApi>(),
            get<GetPropertiesFromDatabase>()
        )
    }

    // ViewModels
    viewModel {
        PokeDetailsActivityViewModel()
    }

    // Adapter
    factory {
        AboutSpinnerAdapter()
    }

    factory {
        EvolutionChainAdapter()
    }

    factory {
        OthersTypeAdapter()
    }

    factory {
        OthersAbilityAdapter()
    }

    factory { (dataList: MutableList<String>) ->
        PokeDetailsGalleryAdapter(
            gallery = dataList
        )
    }

    // UseCases
    factory {
        FetchEvolutionChainFromApi(
            get<EvolutionRepository>()
        )
    }

    factory {
        FetchAbilityFromApi(
            get<AbilityRepository>()
        )
    }

    factory {
        GetAbilityFromDatabase(
            get<AbilityRepository>()
        )
    }

    factory {
        FetchVarietiesFromApi(
            get<VarietyRepository>()
        )
    }

    factory {
        GetVarietiesFromDatabase(
            get<VarietyRepository>()
        )
    }

    factory {
        GetPokesInTypesFromDatabase(
            get<TypeRepository>()
        )
    }

    factory {
        FetchPropertiesFromApi(
            get<PropertyRepository>()
        )
    }

    factory {
        FetchPokesInTypesFromApi(
            get<TypeRepository>()
        )
    }

    factory {
        GetPropertiesFromDatabase(
            get<PropertyRepository>()
        )
    }

    factory {
        GetEvolutionChainFromDatabase(
            get<EvolutionRepository>()
        )
    }

    // Details
    // EvolutionChain
    factory {
        EvolutionRepositoryImpl(
            pokeDetailService = get<PokeDetailService>(),
            evolutionChainDao = get<EvolutionChainDao>(named(evolutionDao))
        ) as EvolutionRepository
    }

    // Varieties
    factory {
        VarietyRepositoryImpl(
            pokeDetailService = get<PokeDetailService>(),
            varietiesDao = get<VarietiesDao>(named(varietyDao))
        ) as VarietyRepository
    }

    // Properties
    factory {
        PropertyRepositoryImpl(
            pokeDetailService = get<PokeDetailService>(),
            propertyDao = get<PropertyDao>(named(propertyDao))
        ) as PropertyRepository
    }

    // Ability
    factory {
        AbilityRepositoryImpl(
            pokeDetailService = get<PokeDetailService>(),
            abilityDao = get<AbilityDao>(named(abilityDao))
        ) as AbilityRepository
    }

    // Properties
    factory {
        TypeRepositoryImpl(
            pokeDetailService = get<PokeDetailService>(),
            typeDao = get<TypeDao>(named(typeDao))
        ) as TypeRepository
    }

    // Retrofit
    single {
        getRetrofitService(
            get<Retrofit>(named(SHARED_RETROFIT))
        )
    }

    // DB
    single(named(detailDatabase)) {
        Room.databaseBuilder(
            androidContext(),
            DetailDatabase::class.java,
            "detail.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single(named(evolutionDao)) {
        get<DetailDatabase>(named(detailDatabase)).evolutionChainDao
    }

    single(named(abilityDao)) {
        get<DetailDatabase>(named(detailDatabase)).abilityDao
    }

    single(named(typeDao)) {
        get<DetailDatabase>(named(detailDatabase)).typeDao
    }

    single(named(propertyDao)) {
        get<DetailDatabase>(named(detailDatabase)).propertyDao
    }

    single(named(varietyDao)) {
        get<DetailDatabase>(named(detailDatabase)).varietiesDao
    }
}

private fun getRetrofitService(retrofit: Retrofit): PokeDetailService = retrofit
    .create(PokeDetailService::class.java)
