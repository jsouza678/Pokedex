package com.souza.home.di

import com.souza.connectivity.Connectivity
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.souza.home.presentation.HomeViewModel
import org.koin.android.ext.koin.androidApplication

private const val pokedexRetrofit = "POKEDEX_RETROFIT"

@Suppress("RemoveExplicitTypeArguments", "USELESS_CAST")
val homeModule = module {

    // ViewModels
    viewModel {
        HomeViewModel()
    }

    factory{
        Connectivity(
            androidApplication()
        )
    }
}
