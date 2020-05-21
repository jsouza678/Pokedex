package souza.home.com.pokedexapp.di

import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import souza.home.com.connectivity.Connectivity
import souza.home.com.pokedexapp.data.pokedex.AbilityRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.EvolutionRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.PokemonRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.PropertyRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.SearchRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.VarietyRepositoryImpl
import souza.home.com.pokedexapp.data.pokedex.local.AbilityDao
import souza.home.com.pokedexapp.data.pokedex.local.EvolutionChainDao
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDao
import souza.home.com.pokedexapp.data.pokedex.local.PokemonDatabase
import souza.home.com.pokedexapp.data.pokedex.local.PropertyDao
import souza.home.com.pokedexapp.data.pokedex.local.VarietiesDao
import souza.home.com.pokedexapp.data.pokedex.remote.PokedexService
import souza.home.com.pokedexapp.data.pokedex.remote.model.ability.AbilitiesRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.type.TypeRoot
import souza.home.com.pokedexapp.data.pokedex.remote.model.variety.Varieties
import souza.home.com.pokedexapp.domain.repository.AbilityRepository
import souza.home.com.pokedexapp.domain.repository.EvolutionRepository
import souza.home.com.pokedexapp.domain.repository.PokemonRepository
import souza.home.com.pokedexapp.domain.repository.PropertyRepository
import souza.home.com.pokedexapp.domain.repository.SearchRepository
import souza.home.com.pokedexapp.domain.repository.VarietyRepository
import souza.home.com.pokedexapp.domain.usecase.FetchAbilityFromApi
import souza.home.com.pokedexapp.domain.usecase.FetchEvolutionChainFromApi
import souza.home.com.pokedexapp.domain.usecase.FetchPokesFromApi
import souza.home.com.pokedexapp.domain.usecase.FetchPropertiesFromApi
import souza.home.com.pokedexapp.domain.usecase.FetchVarietiesFromApi
import souza.home.com.pokedexapp.domain.usecase.GetAbilityFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetEvolutionChainFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetPokesFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetPropertiesFromDatabase
import souza.home.com.pokedexapp.domain.usecase.GetVarietiesFromDatabase
import souza.home.com.pokedexapp.domain.usecase.SearchPokesById
import souza.home.com.pokedexapp.domain.usecase.SearchPokesByName
import souza.home.com.pokedexapp.presentation.home.HomeViewModel
import souza.home.com.pokedexapp.presentation.pokecatalog.PokeCatalogViewModel
import souza.home.com.pokedexapp.presentation.pokedetails.DetailsGalleryAdapter
import souza.home.com.pokedexapp.presentation.pokedetails.DetailsViewModel
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.about.AboutSpinnerAdapter
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.about.AboutViewModel
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.evolutionchain.EvolutionChainAdapter
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.evolutionchain.EvolutionChainViewModel
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.others.OthersAbilityAdapter
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.others.OthersTypeAdapter
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.others.OthersViewModel
import souza.home.com.pokedexapp.presentation.pokedetails.pokeattributes.stats.StatsViewModel
import souza.home.com.pokedexapp.presentation.search.SearchViewModel
import souza.home.com.pokedexapp.utils.Constants

private const val pokedexRetrofit = "POKEDEX_RETROFIT"
private const val pokemonDatabase = "POKEMON_DATABASE"
private const val pokemonDao = "POKEMON_DAO"
private const val varietyDao = "VARIETY_DAO"
private const val propertyDao = "PROPERTY_DAO"
private const val abilityDao = "ABILITY_DAO"
private const val evolutionDao = "EVOLUTION_CHAIN_DAO"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val pokedexModule = module {

    // ViewModels
    viewModel {
        PokeCatalogViewModel(
            get<GetPokesFromDatabase>(),
            get<FetchPokesFromApi>()
        )
    }

    viewModel {
        HomeViewModel()
    }

    viewModel {
        SearchViewModel(
            get<SearchPokesByName>(),
            get<SearchPokesById>()
        )
    }

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
            get<GetAbilityFromDatabase>()
        )
    }

    viewModel { (pokeId: Int) ->
        DetailsViewModel(
            pokeId,
            get<FetchVarietiesFromApi>(),
            get<GetVarietiesFromDatabase>(),
            get<FetchPropertiesFromApi>(),
            get<GetPropertiesFromDatabase>()
        )
    }

    // Adapter
    factory { (dataList: MutableList<Varieties>) ->
        AboutSpinnerAdapter(
            context = get(),
            pokeVariations = dataList
        )
    }

    factory { (dataList: MutableList<String>) ->
        EvolutionChainAdapter(
            context = get(),
            evolutionChain = dataList
        )
    }

    factory { (dataList: MutableList<TypeRoot>) ->
        OthersTypeAdapter(
            context = get(),
            pokeTypes = dataList
        )
    }

    factory { (dataList: MutableList<AbilitiesRoot>) ->
        OthersAbilityAdapter(
            context = get(),
            pokeAbilities = dataList
        )
    }

    factory { (dataList: MutableList<String>) ->
        DetailsGalleryAdapter(
            context = get(),
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
        FetchPropertiesFromApi(
            get<PropertyRepository>()
        )
    }

    factory {
        GetPropertiesFromDatabase(
            get<PropertyRepository>()
        )
    }

    factory {
        SearchPokesById(
            get<SearchRepository>()
        )
    }

    factory {
        SearchPokesByName(
            get<SearchRepository>()
        )
    }

    factory {
        FetchPokesFromApi(
            get<PokemonRepository>()
        )
    }

    factory {
        GetPokesFromDatabase(
            get<PokemonRepository>()
        )
    }

    factory {
        GetEvolutionChainFromDatabase(
            get<EvolutionRepository>()
        )
    }

    // Home
    factory {
        PokemonRepositoryImpl(
            pokedexService = get<PokedexService>(),
            pokemonDao = get<PokemonDao>(named(pokemonDao))
        ) as PokemonRepository
    }

    // Details
    // EvolutionChain
    factory {
        EvolutionRepositoryImpl(
            pokedexService = get<PokedexService>(),
            evolutionChainDao = get<EvolutionChainDao>(named(evolutionDao))
        ) as EvolutionRepository
    }

    // Varieties
    factory {
        VarietyRepositoryImpl(
            pokedexService = get<PokedexService>(),
            varietiesDao = get<VarietiesDao>(named(varietyDao))
        ) as VarietyRepository
    }

    // Properties
    factory {
        PropertyRepositoryImpl(
            pokedexService = get<PokedexService>(),
            propertyDao = get<PropertyDao>(named(propertyDao))
        ) as PropertyRepository
    }

    // Search
    factory {
        SearchRepositoryImpl(
            pokemonDao = get<PokemonDao>(named(pokemonDao))
        ) as SearchRepository
    }

    // Connectivity
    factory {
        Connectivity(
            androidApplication()
        )
    }

    // Ability
    factory {
        AbilityRepositoryImpl(
            pokedexService = get<PokedexService>(),
            abilityDao = get<AbilityDao>(named(abilityDao))
        ) as AbilityRepository
    }

    // Retrofit
    single {
        getRetrofitService(
            get<Retrofit>(named(pokedexRetrofit))
        )
    }

    single(named(pokedexRetrofit)) {
        createRetrofit()
    }

    // DB
    single(named(pokemonDatabase)) {
        Room.databaseBuilder(
            androidContext(),
            PokemonDatabase::class.java,
            "pokedex.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    // DAO
    single(named(pokemonDao)) {
        get<PokemonDatabase>(named(pokemonDatabase)).pokemonDao
    }

    single(named(evolutionDao)) {
        get<PokemonDatabase>(named(pokemonDatabase)).evolutionChainDao
    }

    single(named(abilityDao)) {
        get<PokemonDatabase>(named(pokemonDatabase)).abilityDao
    }

    single(named(propertyDao)) {
        get<PokemonDatabase>(named(pokemonDatabase)).propertyDao
    }

    single(named(varietyDao)) {
        get<PokemonDatabase>(named(pokemonDatabase)).varietiesDao
    }
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createRetrofit(): Retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.POKE_API_BASE_URL)
    .build()

private fun getRetrofitService(retrofit: Retrofit): PokedexService =
    retrofit.create(PokedexService::class.java)
