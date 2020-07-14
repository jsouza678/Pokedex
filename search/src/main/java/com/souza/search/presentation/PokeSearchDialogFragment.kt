package com.souza.search.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.souza.extensions.gone
import com.souza.extensions.observeOnce
import com.souza.extensions.visible
import com.souza.pokecatalog.domain.model.Pokemon
import com.souza.pokedetail.presentation.pokedetails.PokeDetailsActivity
import com.souza.search.R
import com.souza.search.databinding.FragmentPokeSearchDialogBinding
import com.souza.search.utils.Constants.Companion.DELAY_LONG
import com.souza.search.utils.Constants.Companion.EMPTY_STRING
import com.souza.search.utils.Constants.Companion.TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class PokeSearchDialogFragment : DialogFragment() {

    private val adapter by inject<PokeSearchAdapter>()
    private lateinit var layoutManager: GridLayoutManager
    private val viewModel by viewModel<PokeSearchViewModel>()
    private lateinit var binding: FragmentPokeSearchDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentPokeSearchDialogBinding.inflate(LayoutInflater.from(context))

        initSearchButtonClickListener()
        setTransitionToPokeDetails()
        initRecyclerview()
        setupButtonDismiss()

        return setupDialog().create()
    }

    private fun setupDialog(): AlertDialog.Builder {
        val alert = AlertDialog.Builder(context)
        alert.setView(binding.root)

        return alert
    }

    private fun initSearchButtonClickListener() {
        binding.searchButtonSearchDialog.setOnClickListener {
            processSearchInput()
        }
    }

    private fun processSearchInput() {
        binding.inputEditTextSearchDialog.also { searchInputText ->
            when (EMPTY_STRING) {
                searchInputText.text.toString() -> {
                    searchInputText.error = getString(R.string.input_text_search_dialog)
                }
                else -> {
                    initSearchObserver()
                }
            }
        }
    }

    private fun initSearchObserver() {
        viewModel.searchPokesByName(binding.inputEditTextSearchDialog.text.toString())
            .observeOnce(this@PokeSearchDialogFragment, Observer { pokemonList ->
                pokemonList?.let { showSearchedPokes(it) } ?: errorMessage()
            })
    }

    private fun showSearchedPokes(pokemonList: List<Pokemon>) {
        adapter.submitList(pokemonList as MutableList<Pokemon>)
        val textResult = getString(R.string.pokemon_found_search_1) +
                pokemonList.size +
                getString(R.string.pokemon_found_search_4)
        binding.textViewLabelSearchDialog.text = textResult
    }

    private fun setupButtonDismiss() {
        binding.buttonDismissCustomSearchDialog.setOnClickListener {
            dismiss()
        }
    }

    private fun initRecyclerview() {
        layoutManager = GridLayoutManager(context, TWO_COLUMN_GRID_LAYOUT_RECYCLER_VIEW)
        binding.recyclerViewPokeSearchDialog.layoutManager = layoutManager
        binding.recyclerViewPokeSearchDialog.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun toggleErrorVisibility() {
        binding.containerLayoutDefaultSearchDialog.gone()
        binding.containerLayoutErrorSearchDialog.visible()
    }

    private fun toggleBackErrorVisibility() {
        binding.containerLayoutDefaultSearchDialog.visible()
        binding.containerLayoutErrorSearchDialog.gone()
    }

    private fun errorMessage() {
        toggleErrorVisibility()
        Handler().postDelayed({
            toggleBackErrorVisibility()
        }, DELAY_LONG)
    }

    private fun setTransitionToPokeDetails() {
        adapter.onItemClick = { pokemon ->
            startDetailsActivity(pokemon)
        }
    }

    private fun startDetailsActivity(pokemon: Pokemon) {
        val pokeId = pokemon.id
        val pokeName = pokemon.name

        val intent = Intent(activity, PokeDetailsActivity::class.java)
        intent.putExtra("pokeId", pokeId)
        intent.putExtra("pokeName", pokeName)
        activity?.startActivity(intent)
        dismiss()
    }
}
