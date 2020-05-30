package com.souza.pokecatalog.presentation.pokecatalog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.GridLayoutManager
import com.souza.pokecatalog.domain.usecase.FetchPokesFromApi
import com.souza.pokecatalog.domain.usecase.GetPokesFromDatabase
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PokeCatalogViewModelTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val getPokesFromDatabase: GetPokesFromDatabase = mockk()
    private val fetchPokesFromApi: FetchPokesFromApi = mockk()
    private val pokeCatalogViewModel = spyk(
        PokeCatalogViewModel(
            getPokesFromDatabase,
            fetchPokesFromApi
        )
    )

    @Test
    fun updateConnectivityStatus_setsConnectivityStatus() {
        val hasNetworkConnectivity = false

        pokeCatalogViewModel.updateConnectivityStatus(hasNetworkConnectivity)

        Assert.assertEquals(hasNetworkConnectivity, pokeCatalogViewModel.hasNetworkConnectivity)
    }

    @Test
    fun `GIVEN the user is scrolling the screen WHEN the user is on the end of list THEN returns true`() {
        val gridLayoutManager: GridLayoutManager = mockk()

        every { gridLayoutManager.childCount } returns 1
        every { gridLayoutManager.itemCount } returns 1
        every { gridLayoutManager.findFirstVisibleItemPosition() } returns 2

        Assert.assertEquals(true, pokeCatalogViewModel.itIsTheListEnd(gridLayoutManager))
    }

    @Test
    fun `GIVEN the user is scrolling the screen WHEN there are no items THEN returns true`() {
        val gridLayoutManager: GridLayoutManager = mockk()

        every { gridLayoutManager.childCount } returns 0
        every { gridLayoutManager.itemCount } returns 0
        every { gridLayoutManager.findFirstVisibleItemPosition() } returns 0

        Assert.assertEquals(true, pokeCatalogViewModel.itIsTheListEnd(gridLayoutManager))
    }

    @Test
    fun `GIVEN the user is scrolling the screen WHEN the first page was loaded and the user is on the 20th element THEN returns true`() {
        val gridLayoutManager: GridLayoutManager = mockk()

        every { gridLayoutManager.childCount } returns 20
        every { gridLayoutManager.itemCount } returns 20
        every { gridLayoutManager.findFirstVisibleItemPosition() } returns 1

        Assert.assertEquals(true, pokeCatalogViewModel.itIsTheListEnd(gridLayoutManager))
    }
}
